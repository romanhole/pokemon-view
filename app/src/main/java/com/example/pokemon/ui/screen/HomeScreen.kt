package com.example.pokemon.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.pokemon.R
import com.example.pokemon.ui.navigation.Routes.AboutScreen.toAboutScreen
import com.example.pokemon.ui.navigation.Routes.PokemonScreen.toPokemonScreen
import com.example.pokemon.data.network.models.PokemonItem
import com.example.pokemon.ui.components.ErrorDialog
import com.example.pokemon.ui.components.PokemonCard
import com.example.pokemon.ui.data.UiState
import com.example.pokemon.ui.theme.Dimen
import com.example.pokemon.ui.viewmodel.HomeViewModel
import kotlinx.coroutines.flow.update

@Composable
fun HomeScreen(
    dimens: Dimen,
    viewModel: HomeViewModel = hiltViewModel(),
    navController: NavController
) {
    val state by viewModel.state.collectAsState()
    var isErrorDialogOpen: Pair<Boolean, Int> by remember {
        mutableStateOf(Pair(false, 0))
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.getPokemons(null)
    }

    when(state) {
        is UiState.Idle -> Unit

        is UiState.Success -> {
            viewModel.pageState.update { null }

            LaunchHomeScreen(
                dimens = dimens,
                pokemonItems = (state as UiState.Success<List<PokemonItem>>).ret,
                navigateToPokemonScreen = { id ->
                    navController.toPokemonScreen(id)
                },
                nextPokemons = {
                    viewModel.pageState.update { HomeViewModel.PageState.NextPage }
               },
                previousPokemons = {
                    viewModel.pageState.update { HomeViewModel.PageState.PreviousPage }
               },
                navigateToAboutScreen = {
                    navController.toAboutScreen()
                }
            )
        }

        is UiState.Error -> isErrorDialogOpen = Pair(true, (state as UiState.Error).error)
    }

    if (isErrorDialogOpen.first) {
        ErrorDialog(
            text = stringResource(isErrorDialogOpen.second),
            onDismissRequest = {
                isErrorDialogOpen = Pair(false, 0)
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LaunchHomeScreen(
    dimens: Dimen,
    pokemonItems: List<PokemonItem>,
    navigateToPokemonScreen: (Int) -> Unit,
    navigateToAboutScreen: () -> Unit,
    nextPokemons: () -> Unit,
    previousPokemons: () -> Unit
) {
    var isSearchBarVisible by remember { mutableStateOf(false) }
    var text by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            if (isSearchBarVisible) {
                SearchBar(
                    text = text,
                    onSearchClose = {
                        text = ""
                        isSearchBarVisible = false
                    },
                    onTextChanged = {
                        text = it
                    },
                    searchPokemonById = {
                        navigateToPokemonScreen(text.toInt())
                    }
                )
            } else {
                TopAppBar(
                    title = {
                        Image(
                            painter = painterResource(id = R.drawable.ic_pokedex_logo),
                            contentDescription = null)
                    },
                    actions = {
                        IconButton(onClick = {
                            isSearchBarVisible = true
                        }
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Search,
                                contentDescription = null
                            )
                        }
                        IconButton(onClick = navigateToAboutScreen) {
                            Icon(
                                imageVector = Icons.Outlined.Info,
                                contentDescription = null
                            )
                        }
                    }
                )
            }
        }
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .padding(it)
        ) {
            items(pokemonItems) { pokemon ->
                PokemonCard(pokemonItem = pokemon) {
                    navigateToPokemonScreen(pokemon.numberPokedex)
                }
            }

            item {
                Button(
                    onClick = previousPokemons,
                    modifier = Modifier
                        .padding(dimens.default)
                ) {
                    Text(text = stringResource(R.string.previous_button))
                }
            }

            item {
                Button(
                    onClick = nextPokemons,
                    modifier = Modifier
                        .padding(dimens.default)
                ) {
                    Text(text = stringResource(R.string.next_button))
                }
            }
        }
    }
}
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchBar(
    text: String,
    onSearchClose: () -> Unit,
    onTextChanged: (String) -> Unit,
    searchPokemonById: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    val onImeAction = KeyboardActions(
        onDone = {
            searchPokemonById()
            keyboardController?.hide()
        }
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = text,
            onValueChange = { query ->
                onTextChanged(query)
            },
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp),
            placeholder = { Text(stringResource(R.string.search_by_pokemon_number)) },
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
            ),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            leadingIcon = {
                IconButton(onClick = { searchPokemonById() }) {
                    Icon(Icons.Outlined.Search, contentDescription = null)
                }
            },
            trailingIcon = {
                IconButton(onClick = onSearchClose) {
                    Icon(Icons.Default.Close, contentDescription = null)
                }
            },
            keyboardActions = onImeAction
        )
    }
}
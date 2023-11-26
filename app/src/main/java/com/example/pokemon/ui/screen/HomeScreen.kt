package com.example.pokemon.ui.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.pokemon.navigation.Routes.PokemonScreen.toPokemonScreen
import com.example.pokemon.network.models.PokemonItem
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
                nextPokemons = { viewModel.pageState.update { HomeViewModel.PageState.NextPage } },
                previousPokemons = { viewModel.pageState.update { HomeViewModel.PageState.PreviousPage } }
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
    nextPokemons: () -> Unit,
    previousPokemons: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Pokedex") }
            )
        }
    ) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(200.dp),
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
                    Text(text = "previous")
                }
            }

            item {
                Button(
                    onClick = nextPokemons,
                    modifier = Modifier
                        .padding(dimens.default)
                ) {
                    Text(text = "next")
                }
            }
        }
    }
}
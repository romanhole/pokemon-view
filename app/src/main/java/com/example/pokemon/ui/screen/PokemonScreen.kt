package com.example.pokemon.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.pokemon.R
import com.example.pokemon.data.network.models.Pokemon
import com.example.pokemon.ui.components.ErrorDialog
import com.example.pokemon.ui.data.UiState
import com.example.pokemon.ui.theme.Dimen
import com.example.pokemon.ui.viewmodel.PokemonViewModel
import java.util.Locale

@Composable
fun PokemonScreen(
    dimens: Dimen,
    id: Int,
    navController: NavController,
    viewModel: PokemonViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    var isErrorDialogOpen: Pair<Boolean, Int> by remember {
        mutableStateOf(Pair(false, 0))
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.getPokemonById(id)
    }

    when(state) {
        is UiState.Idle -> Unit
        
        is UiState.Success -> PokemonDetails(
            dimens = dimens,
            pokemon = (state as UiState.Success<Pokemon>).ret,
            popBackStack = { navController.popBackStack() }
        )

        is UiState.Error -> isErrorDialogOpen = Pair(true, (state as UiState.Error).error)
    }

    if (isErrorDialogOpen.first) {
        ErrorDialog(
            text = stringResource(isErrorDialogOpen.second),
            onDismissRequest = {
                isErrorDialogOpen = Pair(false, 0)
                navController.popBackStack()
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonDetails(
    dimens: Dimen,
    pokemon: Pokemon,
    popBackStack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    IconButton(onClick = popBackStack ) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(
                    top = padding.calculateTopPadding(),
                    bottom = dimens.default,
                    start = dimens.spaceXXMedium,
                    end = dimens.spaceXXMedium
                )
        ) {
            AsyncImage(
                model = pokemon.sprites.front_default,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.5f),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(dimens.default))

            Text(
                text = stringResource(R.string.pokemon_number, pokemon.id),
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = dimens.fontLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(dimens.default))

            Text(
                text = pokemon.name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() },
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = dimens.fontLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(dimens.spaceMedium))

            Text(
                text = stringResource(R.string.stats),
                fontSize = dimens.fontLarge
            )

            Spacer(modifier = Modifier.height(dimens.spaceSmall))

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(dimens.spaceSmall)
            ) {
                items(pokemon.stats) { stat ->
                    Text(text = "${stat.stat.name}: ${stat.base_stat}")
                }
            }

            Spacer(modifier = Modifier.height(dimens.spaceMedium))

            Text(
                text = stringResource(R.string.types),
                fontSize = dimens.fontLarge
            )

            Spacer(modifier = Modifier.height(dimens.spaceSmall))

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(dimens.spaceSmall)
            ) {
                items(pokemon.types) { type ->
                    Text(text = type.type.name)
                }
            }
        }
    }
}
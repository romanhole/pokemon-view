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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.pokemon.network.models.Pokemon
import com.example.pokemon.ui.components.ErrorDialog
import com.example.pokemon.ui.data.UiState
import com.example.pokemon.ui.theme.Dimen
import com.example.pokemon.ui.viewmodel.PokemonViewModel
import java.util.Locale

@Composable
fun PokemonScreen(
    dimens: Dimen,
    id: Int,
    viewModel: PokemonViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.getPokemonById(id)
    }

    when(state) {
        is UiState.Idle -> Unit
        
        is UiState.Success -> PokemonDetails(
            dimens = dimens,
            pokemon = (state as UiState.Success<Pokemon>).ret
        )

        is UiState.Error -> ErrorDialog(text = stringResource((state as UiState.Error).error))
    }
}

@Composable
fun PokemonDetails(
    dimens: Dimen,
    pokemon: Pokemon
) {
    Column(
        modifier = Modifier
            .padding(dimens.default)
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
            text = "Nº ${pokemon.id}",
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
            text = "Stats:",
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
            text = "Types:",
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
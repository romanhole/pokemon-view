package com.example.pokemon.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.pokemon.network.models.PokemonItem
import com.example.pokemon.ui.components.ErrorDialog
import com.example.pokemon.ui.data.UiState
import com.example.pokemon.ui.theme.Dimen
import com.example.pokemon.ui.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    dimens: Dimen,
    viewModel: HomeViewModel = hiltViewModel(),
    navController: NavController
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.getPokemons(null)
    }

    when(state) {
        is UiState.Idle -> Unit

        is UiState.Success -> LaunchHomeScreen(
            dimens = dimens,
            pokemonItems = (state as UiState.Success<List<PokemonItem>>).ret
        )

        is UiState.Error -> ErrorDialog(text = stringResource((state as UiState.Error).error))
    }

    //navController.toPokemonScreen(1)
}

@Composable
private fun LaunchHomeScreen(
    dimens: Dimen,
    pokemonItems: List<PokemonItem>
) {
    Column {
        for (item in pokemonItems) {
            Text(
                text = "pokemon name = ${item.name}\n" +
                        "pokemon id = ${item.numberPokedex}\n" +
                        "pokemon url = ${item.imageUrl}"
            )
            Spacer(
                modifier = Modifier.height(dimens.default)
            )
        }
    }
}
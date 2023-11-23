package com.example.pokemon.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pokemon.network.models.Pokemon
import com.example.pokemon.ui.components.ErrorDialog
import com.example.pokemon.ui.data.UiState
import com.example.pokemon.ui.viewmodel.PokemonViewModel

@Composable
fun PokemonScreen(
    id: Int,
    viewModel: PokemonViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.getPokemonById(id)
    }

    when(state) {
        is UiState.Idle -> Unit
        
        is UiState.Success -> PokemonDetails(pokemon = (state as UiState.Success<Pokemon>).ret)

        is UiState.Error -> ErrorDialog(text = stringResource((state as UiState.Error).error))
    }
}

@Composable
fun PokemonDetails(
    pokemon: Pokemon
) {
    Column {
        Text(
            text = "pokemon id = ${pokemon.id}\n" +
                    "pokemon name = ${pokemon.name}\n" +
                    "pokemon sprite url = ${pokemon.sprites.front_default}\n"
        )

        Text(text = "pokemon stats:\n")
        for (stats in pokemon.stats) {
            Text(text = "stat name = ${stats.stat.name} and base value = ${stats.base_stat}\n")
        }

        Text(text = "pokemon types:\n")
        for (types in pokemon.types) {
            Text(text = "type name = ${types.type.name}\n")
        }
    }
}
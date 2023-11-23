package com.example.pokemon.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemon.network.models.Pokemon
import com.example.pokemon.repository.PokemonRepository
import com.example.pokemon.ui.data.ResponseData
import com.example.pokemon.ui.data.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class PokemonViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository
): ViewModel() {

    private val _state = MutableStateFlow<UiState<Pokemon>>(UiState.Idle)
    val state = _state.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), UiState.Idle)

    suspend fun getPokemonById(id: Int){
        when(val ret = pokemonRepository.getPokemon(id)) {
            is ResponseData.Success -> _state.update { UiState.Success(ret.ret) }
            is ResponseData.Error -> _state.update { UiState.Error(ret.error.resId) }
        }
    }


}
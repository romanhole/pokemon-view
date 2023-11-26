package com.example.pokemon.ui.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemon.network.models.Pokemon
import com.example.pokemon.network.models.PokemonItem
import com.example.pokemon.network.models.PokemonResult
import com.example.pokemon.repository.PokemonRepository
import com.example.pokemon.ui.data.ResponseData
import com.example.pokemon.ui.data.UiState
import com.example.pokemon.ui.data.enums.EnumErrorResponse.FAILED_GET_LIMIT_URL
import com.example.pokemon.ui.data.enums.EnumErrorResponse.FAILED_GET_OFFSET_URL
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

const val firstPokemonsUrl = "https://pokeapi.co/api/v2/pokemon/?limit=10&offset=0"

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository
): ViewModel() {

    private var nextUrl: String? = null
    private var previousUrl: String? = null

    val pageState = MutableStateFlow<PageState?>(null)

    private val _state = MutableStateFlow<UiState<List<PokemonItem>>>(UiState.Idle)
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        UiState.Idle
    )

    init {
        viewModelScope.launch {
            pageState.asStateFlow().collect { newState ->
                when(newState) {
                    is PageState.NextPage -> getPokemons(nextUrl)

                    is PageState.PreviousPage -> getPokemons(previousUrl)

                    null -> Unit
                }
            }
        }
    }

    suspend fun getPokemons(url: String?) {
        val urlVerified = url ?: firstPokemonsUrl

        when(val ret = getLimitAndOffset(urlVerified)) {
            is ResponseData.Success -> {
                val pokemonList = pokemonRepository.getPokemonList(ret.ret.first, ret.ret.second)
                when(pokemonList) {
                    is ResponseData.Success -> {
                        nextUrl = pokemonList.ret.next
                        previousUrl = pokemonList.ret.previous

                        when(val pokemonItemList = getPokemonList(pokemonList.ret.results)) {
                            is ResponseData.Success -> _state.update {
                                UiState.Success(pokemonItemList.ret)
                            }

                            is ResponseData.Error -> _state.update {
                                UiState.Error(pokemonItemList.error.resId)
                            }
                        }
                    }
                    is ResponseData.Error -> _state.update {
                        UiState.Error(pokemonList.error.resId)
                    }
                }
            }

            is ResponseData.Error -> _state.update {
                UiState.Error(ret.error.resId)
            }
        }
    }

    private suspend fun getPokemonList(pokemonList: List<PokemonResult>): ResponseData<List<PokemonItem>> {
        val pokemonItemList: MutableList<PokemonItem> = mutableListOf()
        for (pokemonResult in pokemonList) {
            val pokemon = getPokemonById(
                getPokemonId(pokemonResult.url).toInt()
            )
            when(pokemon) {
                is ResponseData.Success -> {
                    pokemonItemList.add(
                        PokemonItem(
                            numberPokedex = pokemon.ret.id,
                            name = pokemon.ret.name,
                            imageUrl = pokemon.ret.sprites.front_default
                        )
                    )
                }

                is ResponseData.Error -> {
                    return ResponseData.Error(pokemon.error)
                }
            }
        }

        return ResponseData.Success(pokemonItemList)
    }

    suspend fun getPokemonById(id: Int): ResponseData<Pokemon> {
        return when(val ret = pokemonRepository.getPokemon(id)) {
            is ResponseData.Success -> ResponseData.Success(ret.ret)
            is ResponseData.Error -> ResponseData.Error(ret.error)
        }
    }

    private fun getLimitAndOffset(url: String): ResponseData<Pair<Int, Int>> {
        val uri = Uri.parse(url)
        val limitValue = uri.getQueryParameter("limit")
        val offsetValue = uri.getQueryParameter("offset")

        limitValue?.let {
            offsetValue?.let {
                return ResponseData.Success(Pair(limitValue.toInt(), offsetValue.toInt()))
            } ?: return ResponseData.Error(FAILED_GET_LIMIT_URL)
        } ?: return ResponseData.Error(FAILED_GET_OFFSET_URL)
    }

    private fun getPokemonId(url: String): String =
        url.removeSuffix("/").substringAfterLast("/")

    sealed class PageState {
        object NextPage: PageState()

        object PreviousPage: PageState()
    }
}
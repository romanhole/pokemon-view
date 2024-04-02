package com.example.pokemon.ui.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemon.data.network.models.Pokemon
import com.example.pokemon.data.network.models.PokemonItem
import com.example.pokemon.data.network.models.PokemonResult
import com.example.pokemon.data.repository.PokemonRepository
import com.example.pokemon.ui.data.ResponseData
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

    private var currentUrl: String = firstPokemonsUrl
    private var nextUrl: String? = null
    private var previousUrl: String? = null
    private val pageState = MutableStateFlow<PageState?>(null)

    private val _loadingState = MutableStateFlow(true)
    val loadingState = _loadingState.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        true
    )

    private val _pokemonList = MutableStateFlow<List<PokemonItem>>(mutableListOf())
    val pokemonList = _pokemonList.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        mutableListOf()
    )

    private val _errorState = MutableStateFlow(Pair(false, 0))
    val errorState = _errorState.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        Pair(false, 0)
    )

    init {
        viewModelScope.launch {
            getPokemons(currentUrl)

            pageState.asStateFlow().collect { newState ->
                when(newState) {
                    is PageState.NextPage -> {
                        _loadingState.update { true }
                        nextUrl?.let { getPokemons(it) }
                    }

                    is PageState.PreviousPage -> {
                        _loadingState.update { true }
                        previousUrl?.let { getPokemons(it) }
                    }

                    null -> Unit
                }
            }
        }
    }

    fun nextPage() {
        pageState.update { PageState.NextPage }
    }

    fun previusPage() {
        pageState.update { PageState.PreviousPage }
    }

    fun dismissDialog() {
        _errorState.update { Pair(false, 0) }
    }

    private suspend fun getPokemons(url: String) {

        when(val ret = getLimitAndOffset(url)) {

            is ResponseData.Success -> {
                val pokemonList = pokemonRepository.getPokemonList(ret.ret.first, ret.ret.second)

                when(pokemonList) {

                    is ResponseData.Success -> {
                        currentUrl = url
                        nextUrl = pokemonList.ret.next
                        previousUrl = pokemonList.ret.previous

                        when(val pokemonItemList = getPokemonList(pokemonList.ret.results)) {

                            is ResponseData.Success -> {
                                pageState.update { null }
                                _pokemonList.update { pokemonItemList.ret }
                                _loadingState.update { false }
                            }

                            is ResponseData.Error -> _errorState.update {
                                Pair(true, pokemonItemList.error.resId)
                            }
                        }
                    }

                    is ResponseData.Error -> _errorState.update {
                        Pair(true, pokemonList.error.resId)
                    }
                }
            }

            is ResponseData.Error -> _errorState.update {
                Pair(true, ret.error.resId)
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

    private suspend fun getPokemonById(id: Int): ResponseData<Pokemon> {
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
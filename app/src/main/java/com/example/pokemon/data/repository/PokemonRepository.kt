package com.example.pokemon.data.repository

import android.util.Log
import com.example.pokemon.data.network.ApiService
import com.example.pokemon.data.network.models.Pokemon
import com.example.pokemon.data.network.models.PokemonList
import com.example.pokemon.ui.data.ResponseData
import com.example.pokemon.ui.data.enums.EnumErrorResponse
import retrofit2.awaitResponse
import javax.inject.Inject

class PokemonRepository @Inject constructor(
    private val apiService: ApiService
){
    companion object {
        private val TAG = PokemonRepository::class.java.simpleName
    }

    suspend fun getPokemonList(limit: Int, offset: Int): ResponseData<PokemonList> {
        runCatching {
            val response = apiService.getPokemons(limit, offset).awaitResponse()

            if (!response.isSuccessful) {
                Log.d(TAG, response.code().toString())
                return ResponseData.Error(EnumErrorResponse.FAILED_RESPONSE)
            }

            val pokemonList = response.body()
            pokemonList?.let {
                return ResponseData.Success(
                    PokemonList(
                        next = it.next,
                        previous = it.previous,
                        results = it.results
                    )
                )
            } ?: return ResponseData.Error(EnumErrorResponse.POKEMON_LIST_RESPONSE_NULL)

        }.getOrElse {
            return ResponseData.Error(EnumErrorResponse.UNEXPECTED_ERROR)
        }
    }


    suspend fun getPokemon(id: Int): ResponseData<Pokemon> {
        runCatching {
            val response = apiService.getPokemonById(id).awaitResponse()

            if (!response.isSuccessful) {
                Log.d(TAG, response.code().toString())
                return ResponseData.Error(EnumErrorResponse.FAILED_RESPONSE)
            }

            val pokemon = response.body()
            pokemon?.let {
                return ResponseData.Success(
                    Pokemon(
                        id = it.id,
                        name = it.name,
                        sprites = it.sprites,
                        stats = it.stats,
                        types = it.types
                    )
                )
            } ?: return ResponseData.Error(EnumErrorResponse.POKEMON_RESPONSE_NULL)

        }.getOrElse {
            return ResponseData.Error(EnumErrorResponse.UNEXPECTED_ERROR)
        }
    }
}
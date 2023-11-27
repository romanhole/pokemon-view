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
        return try {
            val response = apiService.getPokemons(limit, offset).awaitResponse()
            if (response.isSuccessful) {
                val pokemonList = response.body()
                pokemonList?.let {
                    ResponseData.Success(
                        PokemonList(
                            next = it.next,
                            previous = it.previous,
                            results = it.results
                        )
                    )
                } ?: ResponseData.Error(EnumErrorResponse.POKEMON_LIST_RESPONSE_NULL)
            } else {
                Log.d(TAG, response.code().toString())
                ResponseData.Error(EnumErrorResponse.FAILED_RESPONSE)
            }
        } catch (e: Exception) {
            ResponseData.Error(EnumErrorResponse.UNEXPECTED_ERROR)
        }
    }

    suspend fun getPokemon(id: Int): ResponseData<Pokemon> {
        return try {
            val response = apiService.getPokemonById(id).awaitResponse()
            if (response.isSuccessful) {
                val pokemon = response.body()
                pokemon?.let {
                    ResponseData.Success(
                        Pokemon(
                            id = it.id,
                            name = it.name,
                            sprites = it.sprites,
                            stats = it.stats,
                            types = it.types
                        )
                    )
                } ?: ResponseData.Error(EnumErrorResponse.POKEMON_RESPONSE_NULL)
            } else {
                Log.d(TAG, response.code().toString())
                ResponseData.Error(EnumErrorResponse.FAILED_RESPONSE)
            }
        } catch (e: Exception) {
            ResponseData.Error(EnumErrorResponse.UNEXPECTED_ERROR)
        }
    }
}
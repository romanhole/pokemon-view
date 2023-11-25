package com.example.pokemon.network

import com.example.pokemon.network.models.Pokemon
import com.example.pokemon.network.models.PokemonList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    /*@GET("pokemon/?limit=10&offset=0")
    fun getPokemons(*//*@Path("endpoint") endpoint: String*//*): Call<PokemonList>*/

    @GET("pokemon")
    fun getPokemons(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Call<PokemonList>

    @GET("pokemon/{id}")
    fun getPokemonById(@Path("id") id: Int): Call<Pokemon>
}
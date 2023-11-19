package com.example.pokemon.network

import com.example.pokemon.network.models.Pokemon
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("pokemon/?limit=10&offset=0")
    fun getPokemons(): Call<List<Pokemon>>

    @GET("pokemon/{id}")
    fun getPokemonById(@Query("id") id: Int): Call<Pokemon>
}
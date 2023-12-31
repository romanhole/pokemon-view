package com.example.pokemon.data.network.models

data class Pokemon(
    val id: Int,
    val name: String,
    val sprites: PokemonSprites,
    val stats: List<PokemonStat>,
    val types: List<PokemonType>
)
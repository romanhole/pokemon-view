package com.example.pokemon.network.models

data class PokemonStat(
    val base_stat: Int,
    val stat: StatInfo
)

data class StatInfo(
    val name: String
)
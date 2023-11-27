package com.example.pokemon.data.network.models

data class PokemonStat(
    val base_stat: Int,
    val stat: StatInfo
)

data class StatInfo(
    val name: String
)
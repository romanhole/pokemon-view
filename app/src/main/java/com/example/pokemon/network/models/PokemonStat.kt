package com.example.pokemon.network.models

data class PokemonStat(
    val baseStat: Int,
    val stat: StatInfo
)

data class StatInfo(
    val name: String
)
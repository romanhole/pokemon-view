package com.example.pokemon.ui.data

sealed class UiState <out T : Any> {
    object Idle : UiState<Nothing>()
    data class Success<out T : Any>(val ret: T) : UiState<T>()
    data class Error(val error: Int) : UiState<Nothing>()
}
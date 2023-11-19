package com.example.pokemon.ui.data

import com.example.pokemon.ui.data.enums.EnumErrorResponse

sealed class ResponseData<out T : Any> {
    data class Success<out T : Any>(val ret: T) : ResponseData<T>()
    data class Error(val error: EnumErrorResponse) : ResponseData<Nothing>()
}

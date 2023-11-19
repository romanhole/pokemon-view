package com.example.pokemon.ui.data.enums

import com.example.pokemon.R

enum class EnumErrorResponse(val resId: Int) {
    POKEMON_RESPONSE_NULL(R.string.pokemon_response_null),
    FAILED_RESPONSE(R.string.failed_response),
    UNEXPECTED_ERROR(R.string.unexpected_error)
}
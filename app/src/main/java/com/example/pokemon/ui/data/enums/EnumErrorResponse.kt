package com.example.pokemon.ui.data.enums

import com.example.pokemon.R

enum class EnumErrorResponse(val resId: Int) {
    POKEMON_RESPONSE_NULL(R.string.pokemon_response_null),
    POKEMON_LIST_RESPONSE_NULL(R.string.pokemon_list_response_null),
    FAILED_RESPONSE(R.string.failed_response),
    UNEXPECTED_ERROR(R.string.unexpected_error),
    FAILED_GET_LIMIT_URL(R.string.failed_get_limit_url),
    FAILED_GET_OFFSET_URL(R.string.failed_get_offset_url)
}
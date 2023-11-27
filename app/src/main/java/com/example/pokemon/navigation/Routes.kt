package com.example.pokemon.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.pokemon.navigation.Arguments.POKEMON_ID

sealed class Routes(
    val route: String,
    val arguments: List<NamedNavArgument> = listOf()
) {
    object HomeScreen : Routes(route = "home_screen") {
        fun NavController.toHomeScreen() =
            navigate(route) {
                popBackStack(route, true)
            }
    }

    object PokemonScreen : Routes(
        route = "pokemon_screen/{$POKEMON_ID}",
        arguments = listOf(navArgument(POKEMON_ID) {type = NavType.IntType})
    ) {
        fun NavController.toPokemonScreen(
            id: Int
        ) = navigate("pokemon_screen/$id")
    }

    object AboutScreen : Routes(route = "about_screen") {
        fun NavController.toAboutScreen() =
            navigate(route)
    }
}

object Arguments {
    const val POKEMON_ID = "ID"
}

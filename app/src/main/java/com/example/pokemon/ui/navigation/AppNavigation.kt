package com.example.pokemon.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.pokemon.ui.navigation.Arguments.POKEMON_ID
import com.example.pokemon.ui.screen.AboutScreen
import com.example.pokemon.ui.screen.HomeScreen
import com.example.pokemon.ui.screen.PokemonScreen
import com.example.pokemon.ui.theme.Dimen

@Composable
fun AppNavigation(navHostController: NavHostController) {
    val dimens = Dimen()

    NavHost(navController = navHostController, startDestination = Routes.HomeScreen.route) {

        composable(route = Routes.HomeScreen.route) {
            HomeScreen(
                dimens = dimens,
                navController = navHostController
            )
        }

        composable(
            route = Routes.PokemonScreen.route,
            arguments = Routes.PokemonScreen.arguments) {
            val pokemonID = remember { it.arguments?.getInt(POKEMON_ID) ?: 0 }
            PokemonScreen(
                dimens = dimens,
                id = pokemonID,
                navController = navHostController
            )
        }

        composable(route = Routes.AboutScreen.route) {
            AboutScreen(
                dimens = dimens
            )
        }
    }
}
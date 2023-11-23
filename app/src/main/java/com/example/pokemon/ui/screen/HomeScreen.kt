package com.example.pokemon.ui.screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.pokemon.navigation.Routes.PokemonScreen.toPokemonScreen
import com.example.pokemon.ui.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navController: NavController
) {
    navController.toPokemonScreen(1)
}
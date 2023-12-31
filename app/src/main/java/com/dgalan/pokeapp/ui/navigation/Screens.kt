package com.dgalan.pokeapp.ui.navigation

sealed class Screens(val route: String) {
    data object LoginScreen : Screens("login_screen")
    data object RegisterScreen : Screens("register_screen")
    data object PokemonScreen : Screens("pokemon_screen")
    data object PokemonDetailScreen : Screens("pokemon_detail_screen")
}

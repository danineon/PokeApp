package com.dgalan.pokeapp.ui.navigation

sealed class Destinations(val route: String) {
    data object LoginScreen : Destinations("login_screen")
    data object RegisterScreen : Destinations("register_screen")
}

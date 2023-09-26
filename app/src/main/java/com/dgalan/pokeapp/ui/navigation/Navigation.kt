package com.dgalan.pokeapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dgalan.pokeapp.login.ui.screen.LoginScreen
import com.dgalan.pokeapp.register.ui.screen.RegisterScreen
import com.dgalan.pokeapp.ui.navigation.Destinations.LoginScreen
import com.dgalan.pokeapp.ui.navigation.Destinations.RegisterScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = LoginScreen.route) {
        composable(LoginScreen.route) {
            LoginScreen(navController)
        }
        composable(RegisterScreen.route) {
            RegisterScreen(navController)
        }
    }
}
package com.dgalan.pokeapp.ui.navigation

import android.app.Activity
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dgalan.pokeapp.R.drawable
import com.dgalan.pokeapp.authetication.ui.login.screen.LoginScreen
import com.dgalan.pokeapp.authetication.ui.register.screen.RegisterScreen
import com.dgalan.pokeapp.pokemondetail.ui.screen.PokemonDetailScreen
import com.dgalan.pokeapp.pokemons.ui.screen.PokemonScreen
import com.dgalan.pokeapp.ui.navigation.Screens.LoginScreen
import com.dgalan.pokeapp.ui.navigation.Screens.PokemonDetailScreen
import com.dgalan.pokeapp.ui.navigation.Screens.PokemonScreen
import com.dgalan.pokeapp.ui.navigation.Screens.RegisterScreen
import com.dgalan.pokeapp.utils.ANIMATION_TRANSITION_DURATION

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = PokemonScreen.route
    ) {
        composable(
            route = LoginScreen.route
        ) {
            LoginScreen(navController)
        }
        composable(
            route = RegisterScreen.route
        ) {
            RegisterScreen(navController)
        }
        composable(
            route = PokemonScreen.route
        ) {
            PokemonScreen(navController)
        }
        composable(
            route = PokemonDetailScreen.route + "/{pokemonId}",
            arguments = listOf(navArgument("pokemonId") { type = NavType.IntType })
        ) {
            PokemonDetailScreen()
        }
    }
}

private fun AnimatedContentTransitionScope<NavBackStackEntry>.enterTransitionLeft() =
    slideIntoContainer(
        towards = SlideDirection.Left,
        animationSpec = tween(ANIMATION_TRANSITION_DURATION)
    )

private fun AnimatedContentTransitionScope<NavBackStackEntry>.exitTransitionLeft() =
    slideOutOfContainer(
        towards = SlideDirection.Left,
        animationSpec = tween(ANIMATION_TRANSITION_DURATION)
    )

private fun AnimatedContentTransitionScope<NavBackStackEntry>.popEnterTransitionRight() =
    slideIntoContainer(
        towards = SlideDirection.Right,
        animationSpec = tween(ANIMATION_TRANSITION_DURATION)
    )

private fun AnimatedContentTransitionScope<NavBackStackEntry>.popExitTransitionRight() =
    slideOutOfContainer(
        towards = SlideDirection.Right,
        animationSpec = tween(ANIMATION_TRANSITION_DURATION)
    )

private fun AnimatedContentTransitionScope<NavBackStackEntry>.enterTransitionUp() =
    slideIntoContainer(
        towards = SlideDirection.Up,
        animationSpec = tween(ANIMATION_TRANSITION_DURATION)
    )

private fun AnimatedContentTransitionScope<NavBackStackEntry>.exitTransitionUp() =
    slideOutOfContainer(
        towards = SlideDirection.Up,
        animationSpec = tween(ANIMATION_TRANSITION_DURATION)
    )

private fun AnimatedContentTransitionScope<NavBackStackEntry>.popEnterTransitionDown() =
    slideIntoContainer(
        towards = SlideDirection.Down,
        animationSpec = tween(ANIMATION_TRANSITION_DURATION)
    )

private fun AnimatedContentTransitionScope<NavBackStackEntry>.popExitTransitionDown() =
    slideOutOfContainer(
        towards = SlideDirection.Down,
        animationSpec = tween(ANIMATION_TRANSITION_DURATION)
    )

@Composable
fun SetWindowBackgroundColor() {
    val activity = LocalContext.current as Activity
    LaunchedEffect(activity) {
        activity.window.setBackgroundDrawableResource(drawable.bg_window)
    }
}



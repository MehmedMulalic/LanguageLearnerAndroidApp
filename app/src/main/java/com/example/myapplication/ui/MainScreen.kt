package com.example.myapplication.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.auth.AuthScreen
import com.example.myapplication.ui.home.HomeScreen
import kotlinx.serialization.Serializable

@Serializable
object Authentication
@Serializable
object Home

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Authentication) {
        composable<Authentication> {
            AuthScreen(
                onLoginSuccess = {
                    navController.navigate(Home) {
                        popUpTo(Authentication) { inclusive = true }
                    }
                }
            )
        }
        composable<Home> { HomeScreen() }
    }
}
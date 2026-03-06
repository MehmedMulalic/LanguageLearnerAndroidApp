package com.mmulalic.languagelearner.ui.auth

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mmulalic.languagelearner.ui.MainScreen
import com.mmulalic.languagelearner.ui.auth.login.LoginScreen
import com.mmulalic.languagelearner.ui.auth.signup.SignupScreen
import kotlinx.serialization.Serializable

@Serializable
object Login
@Serializable
object Signup
@Serializable
object Home

@Composable
fun AuthenticationScreen() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Login) {
        composable<Login> {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Home) {
                        popUpTo(Login) { inclusive = true }
                    }
                },
                onSignupSelect = {
                    navController.navigate(Signup)
                }
            )
        }
        composable<Signup> {
            SignupScreen(
                onSignupSuccess = {
                    navController.navigate(Home) {
                        popUpTo(Signup) { inclusive = true }
                    }
                },
                onLoginSelect = {
                    navController.navigate(Login)
                }
            )
        }
        composable<Home> { MainScreen(navController) }
    }
}
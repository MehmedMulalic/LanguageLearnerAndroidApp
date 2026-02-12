package com.example.myapplication.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.home.HomeScreen
import com.example.myapplication.ui.profile.ProfileScreen

enum class Destination(
    val route: String,
    val label: String,
    val icon: ImageVector
) {
    STATISTICS("statistics", "Statistics", Icons.Default.BarChart),
    HOME("home", "Home", Icons.Default.Home),
    PROFILE("profile", "Profile", Icons.Default.AccountCircle)
}

@Composable
fun AppNavHost(
    rootNavController: NavHostController,
    bottomNavController: NavHostController,
    startDestination: Destination,
    modifier: Modifier = Modifier
) {
    NavHost(
        bottomNavController,
        startDestination = startDestination.route
    ) {
        Destination.entries.forEach { destination ->
            composable(destination.route) {
                when (destination) {
                    Destination.STATISTICS -> ProfileScreen(rootNavController, modifier) //TODO(Temporary)
                    Destination.HOME -> HomeScreen(modifier)
                    Destination.PROFILE -> ProfileScreen(rootNavController, modifier)
                }
            }
        }
    }
}

@Composable
fun MainScreen(
    rootNavController: NavHostController
) {
    val bottomNavController = rememberNavController()
    val startDestination = Destination.HOME

    var selectedDestination by rememberSaveable { mutableIntStateOf(startDestination.ordinal) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                Destination.entries.forEachIndexed { index, destination ->
                    NavigationBarItem(
                        selected = selectedDestination == index,
                        onClick = {
                            bottomNavController.navigate(route = destination.route)
                            selectedDestination = index
                        },
                        icon = {
                            Icon(
                                destination.icon,
                                contentDescription = destination.label
                            )
                        },
                        label = { Text(destination.label) }
                    )
                }
            }
        }
    ) { contentPadding ->
        AppNavHost(rootNavController, bottomNavController, startDestination, modifier = Modifier.padding(contentPadding))
    }
}

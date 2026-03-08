package com.mmulalic.languagelearner.ui.main

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mmulalic.languagelearner.ui.main.home.HomeScreen
import com.mmulalic.languagelearner.ui.main.profile.ProfileScreen

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
    onSignoutSuccess: () -> Unit,
    bottomNavController: NavHostController,
    startDestination: Destination,
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel
) {
    NavHost(
        bottomNavController,
        startDestination = startDestination.route
    ) {
        Destination.entries.forEach { destination ->
            composable(destination.route) {
                when (destination) {
                    Destination.STATISTICS -> {} //TODO: WIP
                    Destination.HOME -> HomeScreen(mainViewModel, modifier)
                    Destination.PROFILE -> ProfileScreen(onSignoutSuccess, modifier)
                }
            }
        }
    }
}

@Composable
fun MainScreen(
    onSignoutSuccess: () -> Unit
) {
    val mainViewModel: MainViewModel = hiltViewModel()
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
                            bottomNavController.navigate(route = destination.route) {
                                popUpTo(bottomNavController.graph.findStartDestination().id) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
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
        AppNavHost(onSignoutSuccess, bottomNavController, startDestination, Modifier.padding(contentPadding), mainViewModel)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    Scaffold(
        bottomBar = {
            NavigationBar {
                Destination.entries.forEachIndexed { index, destination ->
                    NavigationBarItem(
                        selected = 1 == index,
                        onClick = {},
                        icon = {
                            Icon(
                                destination.icon,
                                contentDescription = destination.label
                            )
                        },
                        label = { Text("IPSUM LOREM") }
                    )
                }
            }
        }
    ) { contentPadding ->
        Text("This is a test", modifier = Modifier.padding(contentPadding))
    }
}
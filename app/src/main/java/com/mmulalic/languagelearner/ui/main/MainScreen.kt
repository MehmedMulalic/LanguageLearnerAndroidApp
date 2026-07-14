package com.mmulalic.languagelearner.ui.main

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mmulalic.languagelearner.data.repository.AuthState
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
    onSignOutSuccess: () -> Unit,
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
                    Destination.PROFILE -> ProfileScreen(onSignOutSuccess, modifier)
                }
            }
        }
    }
}

@Composable
fun MainScaffold(
    selectedDestination: Int,
    onDestinationClicked: (Int, Destination) -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable (Modifier) -> Unit
) {
    Scaffold(
        modifier = modifier,
        bottomBar = {
            NavigationBar(
                modifier = Modifier.height(68.dp)
            ) {
                Destination.entries.forEachIndexed { index, destination ->
                    NavigationBarItem(
                        selected = selectedDestination == index,
                        onClick = { onDestinationClicked(index, destination) },
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
        content(Modifier.padding(contentPadding))
    }
}

@Composable
fun MainScreen(
    onSignOutSuccess: () -> Unit
) {
    val mainViewModel: MainViewModel = hiltViewModel()
    val bottomNavController = rememberNavController()
    val startDestination = Destination.HOME
    val snackbarHostState = remember { SnackbarHostState() }
    val authState by mainViewModel.authState.collectAsState()

    var selectedDestination by rememberSaveable { mutableIntStateOf(startDestination.ordinal) }

    LaunchedEffect(authState) {
        if (authState == AuthState.Unauthenticated) {
            onSignOutSuccess()
        }
    }
    LaunchedEffect(Unit) {
        mainViewModel.sessionEvents.collect {
            snackbarHostState.showSnackbar("Your session expired. Please log in again.")
        }
    }

    MainScaffold(
        selectedDestination = selectedDestination,
        onDestinationClicked = { index, destination ->
            bottomNavController.navigate(route = destination.route) {
                popUpTo(bottomNavController.graph.findStartDestination().id) { saveState = true }
                launchSingleTop = true
                restoreState = true
            }
            selectedDestination = index
        }
    ) { contentModifier ->
        AppNavHost(onSignOutSuccess, bottomNavController, startDestination, contentModifier, mainViewModel)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    var selectedDestination by remember { mutableIntStateOf(Destination.HOME.ordinal) }

    MainScaffold(
        selectedDestination = selectedDestination,
        { index, _ -> selectedDestination = index}
    ) { contentModifier ->
        Text("Preview Content", modifier = contentModifier)
    }
}
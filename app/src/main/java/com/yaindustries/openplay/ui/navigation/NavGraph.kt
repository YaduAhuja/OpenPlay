package com.yaindustries.openplay.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.yaindustries.openplay.ui.screens.LibraryScreen
import com.yaindustries.openplay.ui.screens.home.HomeScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    navigationController: NavigationController
) {

    NavHost(
        navController = navController,
        startDestination = navigationController.getStartDestination()
    ) {
        composable(Screen.Home.route) {
            HomeScreen(navigationController)
        }

        composable(Screen.Library.route) {
            LibraryScreen(navigationController)
        }
    }
}
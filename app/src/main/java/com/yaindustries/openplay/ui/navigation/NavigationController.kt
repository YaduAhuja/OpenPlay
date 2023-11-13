package com.yaindustries.openplay.ui.navigation

import androidx.navigation.NavController

class NavigationController(private val navController: NavController) {
    fun getStartDestination() = Screen.Home.route

    fun navigateToLibraryScreen() {
        navController.navigate(Screen.Library.route)
    }

    fun navigateToHomeScreen() {
        navController.navigate(Screen.Home.route)
    }

    fun navigateToRoute(route: String) {
        if (navController.currentDestination?.route == route)
            return
        navController.navigate(route)
    }
}
package com.yaindustries.openplay.ui.navigation

import androidx.navigation.NavController

class NavigationController(private val navController: NavController) {
    fun getStartDestination() = Screen.Home.route

    fun navigateToRoute(route: String) {
        if (getCurrentRoute() == route)
            return

        navController.navigate(route) {
            getCurrentRoute()?.let {
                popUpTo(it) {
                    saveState = true
                    inclusive = true
                }
            }
            restoreState = true
            launchSingleTop = true
        }
    }

    private fun getCurrentRoute() = navController.currentDestination?.route
}
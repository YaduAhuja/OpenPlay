package com.yaindustries.openplay.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.yaindustries.openplay.ui.screens.HomeScreen
import com.yaindustries.openplay.ui.screens.LibraryScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    navigationOptions: NavigationController,
    paddingValues: PaddingValues
) {

    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            HomeScreen(paddingValues, navigationOptions)
        }

        composable(Screen.Library.route) {
            LibraryScreen(paddingValues, navigationOptions)
        }
    }
}
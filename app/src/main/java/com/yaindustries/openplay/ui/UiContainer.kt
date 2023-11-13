package com.yaindustries.openplay.ui

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.yaindustries.openplay.ui.navigation.BottomBar
import com.yaindustries.openplay.ui.navigation.NavGraph
import com.yaindustries.openplay.ui.navigation.NavigationController
import com.yaindustries.openplay.ui.theme.OpenPlayTheme

@Composable
fun UiContainer() {
    val navController = rememberNavController()
    val navigationController = remember { NavigationController(navController) }
    val currentBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute =
        currentBackStackEntry.value?.destination?.route
            ?: navigationController.getStartDestination()

    OpenPlayTheme {
        Scaffold(
            content = { NavGraph(navController, navigationController, it) },
            bottomBar = { BottomBar(currentRoute, navigationController) }
        )
    }
}
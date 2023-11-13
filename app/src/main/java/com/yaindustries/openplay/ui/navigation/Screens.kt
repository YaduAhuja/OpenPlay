package com.yaindustries.openplay.ui.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("HomeScreen")
    data object Library : Screen("LibraryScreen")
}
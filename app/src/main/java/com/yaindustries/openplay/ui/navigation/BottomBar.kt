package com.yaindustries.openplay.ui.navigation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LibraryMusic
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LibraryMusic
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import kotlinx.collections.immutable.persistentListOf

@Composable
fun BottomBar(navHostController: NavHostController, navigationController: NavigationController) {
    val currentBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentRoute =
        currentBackStackEntry?.destination?.route ?: navigationController.getStartDestination()

    BottomAppBar(
        modifier = Modifier.height(72.dp),
        content = { AppBar(currentRoute, navigationController) }
    )
}

@Composable
fun AppBar(currentRoute: String, navigationController: NavigationController) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        bottomBarActions.forEach {
            BottomBarItem(
                currentRoute = currentRoute,
                actionsInfo = it,
                navigationAction = { navigationController.navigateToRoute(it.route) }
            )
        }
    }
}

@Composable
private fun BottomBarItem(
    currentRoute: String,
    actionsInfo: BottomBarActionsInfo,
    navigationAction: () -> Unit
) {
    val isSelected = currentRoute == actionsInfo.route

    Column(
        modifier = Modifier.clickable(onClick = navigationAction),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = if (isSelected) actionsInfo.selectedIcon else actionsInfo.icon,
            contentDescription = actionsInfo.displayText
        )
        Text(
            text = actionsInfo.displayText,
            fontWeight = if (isSelected) FontWeight.SemiBold else null
        )
    }

}

private val bottomBarActions = persistentListOf(
    BottomBarActionsInfo(
        Icons.Outlined.Home,
        Icons.Filled.Home,
        "Home",
        Screen.Home.route
    ),
    BottomBarActionsInfo(
        Icons.Outlined.LibraryMusic,
        Icons.Filled.LibraryMusic,
        "Library",
        Screen.Library.route
    )
)

@Immutable
private data class BottomBarActionsInfo(
    val icon: ImageVector,
    val selectedIcon: ImageVector,
    val displayText: String,
    val route: String
)

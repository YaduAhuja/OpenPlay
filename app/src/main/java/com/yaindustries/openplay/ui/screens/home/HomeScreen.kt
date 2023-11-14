package com.yaindustries.openplay.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.yaindustries.openplay.ui.navigation.NavigationController

@Composable
fun HomeScreen(navigationController: NavigationController) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        HomeScreenTopRow()
        Text(text = "Recently Played")
        Row {

        }
    }
}

@Composable
private fun HomeScreenTopRow() {
    Row {
        Text(text = "Good Morning")
        Icon(imageVector = Icons.Outlined.Notifications, contentDescription = "Notifications")
        Icon(imageVector = Icons.Outlined.Settings, contentDescription = "Settings")
    }
}


package com.yaindustries.openplay.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.yaindustries.openplay.data.models.SongInfo
import com.yaindustries.openplay.ui.navigation.BottomBar
import com.yaindustries.openplay.ui.navigation.NavGraph
import com.yaindustries.openplay.ui.navigation.NavigationController
import com.yaindustries.openplay.ui.theme.OpenPlayTheme

@Composable
fun UiContainer(viewModel: UiContainerViewModel) {
    val navController = rememberNavController()
    val navigationController = remember { NavigationController(navController) }
    val currentBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute =
        currentBackStackEntry.value?.destination?.route
            ?: navigationController.getStartDestination()
    val snackBarHostState = remember { SnackbarHostState() }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    OpenPlayTheme {
        Scaffold(
            content = {
                ContentContainer(
                    navController,
                    navigationController,
                    it,
                    uiState.playerSongInfo
                )
            },
            snackbarHost = { SnackbarHost(snackBarHostState) },
            bottomBar = { BottomBar(currentRoute, navigationController) }
        )
    }
}

@Composable
private fun ContentContainer(
    navController: NavHostController,
    navigationController: NavigationController,
    paddingValues: PaddingValues,
    playerSongInfo: SongInfo?
) {
    Box(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        NavGraph(navController, navigationController)
        if (playerSongInfo != null) PlayerWidget()
    }
}

@Composable
private fun PlayerWidget() {
    Row(
        modifier = Modifier
            .padding(16.dp) // Margin
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Outlined.Menu,
            contentDescription = "Song Picture",
            modifier = Modifier.weight(0.1f)
        )

        Column(modifier = Modifier.weight(0.6f)) {
            Text(text = "Song Name", fontSize = MaterialTheme.typography.bodyMedium.fontSize)
            Text(text = "Artists Name", fontSize = MaterialTheme.typography.bodyMedium.fontSize)
        }

        Icon(
            imageVector = Icons.Rounded.FavoriteBorder,
            contentDescription = null,
            modifier = Modifier.weight(0.1f)
        )

        Icon(
            imageVector = Icons.Rounded.PlayArrow,
            contentDescription = null,
            modifier = Modifier.weight(0.1f)
        )
    }
}
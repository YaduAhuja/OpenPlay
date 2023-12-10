package com.yaindustries.openplay.ui

import android.provider.MediaStore
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Album
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.Pause
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.yaindustries.openplay.data.models.PlayerInfoAndSongInfo
import com.yaindustries.openplay.data.models.SongInfo
import com.yaindustries.openplay.ui.common.checkOrRequestPermissions
import com.yaindustries.openplay.ui.navigation.BottomBar
import com.yaindustries.openplay.ui.navigation.NavGraph
import com.yaindustries.openplay.ui.navigation.NavigationController
import com.yaindustries.openplay.ui.theme.OpenPlayTheme
import com.yaindustries.openplay.utils.Utilities

@Composable
fun UiContainer(uiContainerVM: UiContainerViewModel = viewModel(factory = UiContainerViewModel.provideFactory())) {
    val navController = rememberNavController()
    val navigationController = remember { NavigationController(navController) }
    val snackBarHostState = remember { SnackbarHostState() }
    val audioPermissionsMap by checkOrRequestPermissions(Utilities.getAudioPermissions())
    val context = LocalContext.current

    OpenPlayTheme {
        Scaffold(
            content = {
                ContentContainer(
                    uiContainerVM,
                    navController,
                    navigationController,
                    it
                )
            },
            snackbarHost = { SnackbarHost(snackBarHostState) },
            bottomBar = { BottomBar(navController, navigationController) }
        )
    }

    if (false !in audioPermissionsMap.values) {
        Log.d(
            "Ui Container",
            "Version : ${MediaStore.getVersion(context, MediaStore.VOLUME_EXTERNAL_PRIMARY)}"
        )
        LaunchedEffect(Unit) {
            uiContainerVM.refreshSongs(context)
            Log.d("Ui Container", "Launched effect")
        }
    }
}

@Composable
private fun ContentContainer(
    uiContainerVM: UiContainerViewModel,
    navController: NavHostController,
    navigationController: NavigationController,
    paddingValues: PaddingValues
) {
    val uiState by uiContainerVM.uiState.collectAsStateWithLifecycle()
    val playerAndSongInfo = uiState.playerInfoAndSongInfo

    Box(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        NavGraph(navController, navigationController)
        if (playerAndSongInfo != null)
            PlayerWidget(
                playerAndSongInfo,
                uiContainerVM::handlePlayPauseClick,
                uiContainerVM::handleFavoriteClick
            )
    }
}

@Composable
private fun PlayerWidget(
    playerInfoAndSongInfo: PlayerInfoAndSongInfo,
    onPlayPauseClick: () -> Unit = {},
    onFavoriteClick: (SongInfo) -> Unit = {}
) {
    val playerInfo = playerInfoAndSongInfo.playerInfo
    val songInfo = playerInfoAndSongInfo.songInfo
    Row(
        modifier = Modifier
            .padding(16.dp) // Margin
            .fillMaxWidth()
            .background(Color.DarkGray)
            .clickable { }
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Rounded.Album,
            contentDescription = "Song Picture",
            modifier = Modifier.weight(0.1f)
        )

        Column(modifier = Modifier.weight(0.6f)) {
            Text(
                text = songInfo.name,
                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                maxLines = 1
            )
            Text(
                text = songInfo.artists,
                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                maxLines = 1
            )
        }

        IconButton(onClick = { onFavoriteClick(songInfo) }) {
            if (songInfo.isFavorite) {
                Icon(
                    imageVector = Icons.Rounded.Favorite,
                    contentDescription = "Favorite Button",
                    modifier = Modifier.weight(0.1f)
                )
            } else {
                Icon(
                    imageVector = Icons.Rounded.FavoriteBorder,
                    contentDescription = "Favorite Button",
                    modifier = Modifier.weight(0.1f)
                )
            }
        }

        IconButton(onClick = onPlayPauseClick) {
            if (playerInfo.isPlaying) {
                Icon(
                    imageVector = Icons.Rounded.Pause,
                    contentDescription = "Pause Button",
                    modifier = Modifier.weight(0.1f)
                )
            } else {
                Icon(
                    imageVector = Icons.Rounded.PlayArrow,
                    contentDescription = "Play Button",
                    modifier = Modifier.weight(0.1f)
                )
            }
        }


    }
}
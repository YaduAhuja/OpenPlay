package com.yaindustries.openplay.ui.screens.library

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.twotone.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yaindustries.openplay.data.models.PlaylistInfo
import com.yaindustries.openplay.data.models.SongInfo
import com.yaindustries.openplay.ui.common.PlaylistCard
import com.yaindustries.openplay.ui.common.SongCard
import com.yaindustries.openplay.ui.navigation.NavigationController
import com.yaindustries.openplay.utils.Constants
import com.yaindustries.openplay.utils.Utilities
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun LibraryScreen(
    navigationController: NavigationController,
    libraryScreenViewModel: LibraryScreenViewModel = viewModel(factory = LibraryScreenViewModel.provideFactory())
) {
    val permissions = Utilities.getAudioPermissions()
    val uiState by libraryScreenViewModel.libraryScreenState.collectAsStateWithLifecycle()
    val permissionAvailable = Utilities.arePermissionsAvailable(LocalContext.current, *permissions)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(Constants.PADDING_MEDIUM),
        verticalArrangement = Arrangement.spacedBy(Constants.PADDING_MEDIUM)
    ) {
        LibraryScreenTopRow()
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(
                Constants.PADDING_SMALL,
                Alignment.Start
            )
        ) {
            Button(onClick = { libraryScreenViewModel.changeLibraryView(LibraryViews.Playlists) }) {
                Text(text = "Playlists")
            }
            Button(onClick = { libraryScreenViewModel.changeLibraryView(LibraryViews.Songs) }) {
                Text(text = "All Songs")
            }
        }

        if (false !in permissionAvailable.values) {
            when (uiState.libraryView) {
                LibraryViews.Playlists -> PlaylistsView()
                LibraryViews.Songs -> SongsView { uiState.songs }
            }
        } else {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Storage Permission is required for this screen to load",
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun LibraryScreenTopRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { /*TODO*/ }, modifier = Modifier.weight(0.15f)) {
            Icon(
                imageVector = Icons.TwoTone.Person,
                contentDescription = "Person Icon",
            )
        }

        Text(
            text = "Your Library",
            modifier = Modifier.weight(0.65f),
            fontWeight = FontWeight.SemiBold,
            fontSize = MaterialTheme.typography.headlineSmall.fontSize
        )

        IconButton(onClick = { /*TODO*/ }, modifier = Modifier.weight(0.15f)) {
            Icon(imageVector = Icons.Outlined.Search, contentDescription = "Search")
        }

        IconButton(onClick = { /*TODO*/ }, modifier = Modifier.weight(0.15f)) {
            Icon(imageVector = Icons.Outlined.Add, contentDescription = "Search")
        }
    }
}

@Composable
private fun PlaylistsView() {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(Constants.PADDING_MEDIUM),
        verticalArrangement = Arrangement.spacedBy(Constants.PADDING_MEDIUM)
    ) {
        items(dummyPlaylistInfoList) {
            PlaylistCard()
        }
    }
}

@Composable
private fun SongsView(getSongsList: () -> ImmutableList<SongInfo>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(Constants.PADDING_SMALL)
    ) {
        items(getSongsList()) {
            SongCard(it)
        }
    }
}

private val dummySongInfo = SongInfo(0, "", "", "", 0, 0, isFavourite = false, isPlaying = false)
private val dummySongInfoList = persistentListOf(*Array(50) { dummySongInfo })
private val dummyPlaylistInfoList = persistentListOf(
    PlaylistInfo(0, "Playlist0", "unknown", persistentListOf(dummySongInfo, dummySongInfo)),
    PlaylistInfo(1, "Playlist1", "unknown", persistentListOf(dummySongInfo)),
    PlaylistInfo(
        2,
        "Playlist2",
        "unknown",
        persistentListOf(dummySongInfo, dummySongInfo, dummySongInfo)
    )
)
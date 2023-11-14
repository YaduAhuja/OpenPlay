package com.yaindustries.openplay.ui.screens.library

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.yaindustries.openplay.ui.navigation.NavigationController
import com.yaindustries.openplay.utils.Utilities

@Composable
fun LibraryScreen(navigationController: NavigationController) {
    val libraryView = rememberSaveable { mutableStateOf(LibraryViews.Playlists) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        LibraryScreenTopRow()
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Button(onClick = {
                Utilities.updateValueByCheckingEquality(
                    libraryView,
                    LibraryViews.Playlists
                )
            }) {
                Text(text = "Playlists")
            }
            Button(onClick = {
                Utilities.updateValueByCheckingEquality(
                    libraryView,
                    LibraryViews.Songs
                )
            }) {
                Text(text = "All Songs")
            }
        }

        when (libraryView.value) {
            LibraryViews.Playlists -> PlaylistsView()
            LibraryViews.Songs -> SongsView()
        }
    }
}

@Composable
private fun LibraryScreenTopRow() {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = Icons.Outlined.Person,
            contentDescription = "Person Icon",
            modifier = Modifier.weight(0.15f)
        )
        Text(text = "Your Library", modifier = Modifier.weight(0.65f))
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
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Playlists View")
    }
}

@Composable
private fun SongsView() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Songs View")
    }
}

private enum class LibraryViews {
    Playlists, Songs
}
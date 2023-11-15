package com.yaindustries.openplay.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.yaindustries.openplay.data.models.PlaylistInfo
import com.yaindustries.openplay.data.interfaces.Info
import com.yaindustries.openplay.ui.common.PlaylistCard
import com.yaindustries.openplay.ui.navigation.NavigationController
import com.yaindustries.openplay.utils.Constants
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun HomeScreen(navigationController: NavigationController) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(Constants.PADDING_MEDIUM),
        modifier = Modifier
            .fillMaxSize()
            .padding(Constants.PADDING_MEDIUM)
    ) {
        HomeScreenTopRow(navigationController)
        HomeScreenMainContent()
    }
}

@Composable
private fun HomeScreenTopRow(navigationController: NavigationController) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Constants.PADDING_SMALL)
    ) {
        Text(
            text = "Good Morning",
            modifier = Modifier.weight(0.8f),
            fontSize = MaterialTheme.typography.headlineSmall.fontSize,
            fontWeight = FontWeight.Bold
        )

        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                imageVector = Icons.Outlined.Notifications,
                contentDescription = "Notifications",
            )
        }

        IconButton(onClick = { /*TODO*/ }, modifier = Modifier.weight(0.1f)) {
            Icon(
                imageVector = Icons.Outlined.Settings,
                contentDescription = "Settings"
            )
        }
    }
}


@Composable
private fun HomeScreenMainContent() {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(Constants.PADDING_SMALL)) {
        items(homeScreenContentRowInfoList) {
            Column(verticalArrangement = Arrangement.spacedBy(Constants.PADDING_SMALL)) {
                Text(
                    text = it.heading,
                    fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                    fontWeight = FontWeight.Bold
                )
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(Constants.PADDING_SMALL)
                ) {
                    items(it.content) {
                        PlaylistCard(modifier = Modifier.width(128.dp))
                    }
                }
            }
        }
    }
}

private data class HomeScreenContentRowInfo(
    val heading: String,
    val content: ImmutableList<Info>
) {}

private val songInfoList =
    persistentListOf(*Array(10) { PlaylistInfo(0, "Playlist0", "Unknown", persistentListOf()) })
private val homeScreenContentRowInfoList = persistentListOf(*Array(10) {
    HomeScreenContentRowInfo(
        "Recently Played",
        songInfoList
    )
})
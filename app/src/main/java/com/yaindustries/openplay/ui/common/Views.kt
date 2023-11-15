package com.yaindustries.openplay.ui.common

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yaindustries.openplay.ui.theme.OpenPlayTheme

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun PlaylistCard(modifier: Modifier = Modifier) {
    BoxWithConstraints(modifier = modifier) {
        val maxWidth = maxWidth
        Column {
            Box(
                modifier = Modifier
                    .background(Color.DarkGray)
                    .fillMaxWidth()
                    .height(maxWidth)
            )
            Text(
                text = "Dummy textdksajnksaj" +
                        "dnksajldmklsadl;sa" +
                        "dsalkdsa\ndnksa" +
                        "dnksajd\n" +
                        "dsakdsa,",
                overflow = TextOverflow.Ellipsis,
                maxLines = 2
            )
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
private fun PlaylistCardPreview() {
    OpenPlayTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Row {
                Spacer(modifier = Modifier.width(8.dp))
                PlaylistCard()
                Spacer(modifier = Modifier.width(8.dp))
                PlaylistCard()
                Spacer(modifier = Modifier.width(8.dp))
                PlaylistCard()
            }
        }
    }
}


@Composable
fun SongCard() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        BoxWithConstraints(modifier = Modifier.weight(0.15f)) {
            Box(
                modifier = Modifier
                    .size(maxWidth)
                    .background(Color.DarkGray)
            )
        }
        Column(modifier = Modifier.weight(0.75f)) {
            Text(text = "Song Header")
            Text(text = "Artists Name", fontSize = MaterialTheme.typography.bodySmall.fontSize)
        }
        IconButton(onClick = { /*TODO*/ }, modifier = Modifier.weight(0.1f)) {
            Icon(imageVector = Icons.Default.MoreVert, contentDescription = "Song Options")
        }
    }
}
package com.yaindustries.openplay.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.yaindustries.openplay.ui.navigation.NavigationController

@Composable
fun HomeScreen(navigationOptions: NavigationController) {
    var useHorizontal by remember { mutableStateOf(true) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        if (useHorizontal) {
            Column {
                Dummy()
                Spacer(
                    modifier = Modifier
                        .height(100.dp)
                        .background(Color.White)
                        .border(5.dp, Color.Blue)
                )
                Dummy()
            }
        } else {
            Row(modifier = Modifier.height(300.dp)) {
                Dummy()
                Spacer(
                    modifier = Modifier
                        .width(100.dp)
                        .background(Color.White)
                        .border(5.dp, Color.Blue)
                )
                Dummy()
            }
        }

        Spacer(
            modifier = Modifier
                .height(20.dp)
                .border(5.dp, Color.Blue)
        )
        Button(onClick = { useHorizontal = !useHorizontal }) {
            Text(text = "Change orientation")
        }

        Spacer(
            modifier = Modifier
                .height(20.dp)
                .border(5.dp, Color.Blue)
        )

        Button(onClick = navigationOptions::navigateToLibraryScreen) {
            Text(text = "Change screen")
        }
    }


}

@Composable
private fun Dummy() {
    Box(
        modifier = Modifier
            .height(100.dp)
            .width(100.dp)
            .background(Color.Yellow)
    )
}



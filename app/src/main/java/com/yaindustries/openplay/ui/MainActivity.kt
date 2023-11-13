package com.yaindustries.openplay.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yaindustries.openplay.OpenPlayApplication

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appContainer = (application as OpenPlayApplication).appContainer
        setContent {
            UiContainer(viewModel(factory = UiContainerViewModel.provideFactory(appContainer)))
        }
    }
}
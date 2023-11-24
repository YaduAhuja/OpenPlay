package com.yaindustries.openplay.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.yaindustries.openplay.OpenPlayApplication
import com.yaindustries.openplay.data.models.SongInfo
import com.yaindustries.openplay.data.services.MediaStoreService
import com.yaindustries.openplay.data.services.SongInfoService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class UiContainerState(val playerSongInfo: SongInfo? = null)

class UiContainerViewModel(
    private val mediaStoreService: MediaStoreService,
    private val songInfoService: SongInfoService
) : ViewModel() {

    private val _uiContainerState = MutableStateFlow(UiContainerState())
    val uiState = _uiContainerState.asStateFlow()

    init {
        viewModelScope.launch {
            collectCurrentPlayingSongInfo()
        }
    }


    private suspend fun collectCurrentPlayingSongInfo() {
        songInfoService.getPlayingSongAsFlow().stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(stopTimeoutMillis = 10000),
            null
        ).collect { songInfo: SongInfo? ->
            _uiContainerState.update { UiContainerState(songInfo) }
        }
    }

    suspend fun refreshSongs(context: Context) {
        mediaStoreService.refreshMediaStore()
    }

    companion object {
        fun provideFactory() =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(
                    modelClass: Class<T>,
                    creationExtras: CreationExtras
                ): T {
                    val application =
                        checkNotNull(creationExtras[APPLICATION_KEY]) as OpenPlayApplication

                    return UiContainerViewModel(
                        application.appContainer.mediaStoreService,
                        application.appContainer.songInfoService
                    ) as T
                }
            }
    }
}
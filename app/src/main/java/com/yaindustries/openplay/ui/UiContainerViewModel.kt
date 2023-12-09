package com.yaindustries.openplay.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.yaindustries.openplay.OpenPlayApplication
import com.yaindustries.openplay.data.models.PlayerInfo
import com.yaindustries.openplay.data.models.SongInfo
import com.yaindustries.openplay.data.services.MediaPlayerService
import com.yaindustries.openplay.data.services.MediaStoreService
import com.yaindustries.openplay.data.services.PlayerInfoService
import com.yaindustries.openplay.data.services.SongInfoService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class UiContainerState(val playerInfo: PlayerInfo? = null, val songInfo: SongInfo? = null)

class UiContainerViewModel(
    private val mediaStoreService: MediaStoreService,
    private val playerSongInfoService: PlayerInfoService,
    private val songInfoService: SongInfoService,
    private val mediaPlayerService: MediaPlayerService
) : ViewModel() {

    private val _uiContainerState = MutableStateFlow(UiContainerState())
    val uiState = _uiContainerState.asStateFlow()

    init {
        viewModelScope.launch {
            collectCurrentPlayingSongInfo()
        }
        mediaPlayerService.startMediaPlayerService()
    }

    fun handlePlayPauseClick() {
        viewModelScope.launch {
            playerSongInfoService.playPauseCurrentSong()
        }
    }

    fun handleFavoriteClick(songInfo: SongInfo) {
        viewModelScope.launch {
            songInfoService.upsert(songInfo.copy(isFavorite = !songInfo.isFavorite))
        }
    }

    private suspend fun collectCurrentPlayingSongInfo() {
        playerSongInfoService.getPlayerSongInfoAsFlow().stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(stopTimeoutMillis = 10000),
            null
        ).collect { playerInfo: PlayerInfo? ->
            var songInfo: SongInfo? = null
            playerInfo?.let {
                songInfo = songInfoService.findSongInfoById(it.songInfoId)
            }
            _uiContainerState.update { UiContainerState(playerInfo, songInfo) }
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
                    extras: CreationExtras
                ): T {
                    val application =
                        checkNotNull(extras[APPLICATION_KEY]) as OpenPlayApplication

                    return UiContainerViewModel(
                        application.appContainer.mediaStoreService,
                        application.appContainer.playerInfoService,
                        application.appContainer.songInfoService,
                        application.appContainer.mediaPlayerService
                    ) as T
                }
            }
    }
}
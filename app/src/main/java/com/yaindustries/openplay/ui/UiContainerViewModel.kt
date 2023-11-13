package com.yaindustries.openplay.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.yaindustries.openplay.AppContainer
import com.yaindustries.openplay.data.models.SongInfo
import com.yaindustries.openplay.data.repositories.SongInfoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class UiContainerState(val playerSongInfo: SongInfo? = null)

class UiContainerViewModel(private val songInfoRepository: SongInfoRepository) : ViewModel() {

    private val _uiContainerState = MutableStateFlow(UiContainerState())
    val uiState = _uiContainerState.asStateFlow()

    init {
        viewModelScope.launch {
            collectSongInfo()
        }
    }

    //    private val songInfoStateFlow: StateFlow<SongInfo?> =
    private suspend fun collectSongInfo() {
        songInfoRepository.getPlayingSong().stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(stopTimeoutMillis = 10000),
            null
        ).collect { songInfo: SongInfo? ->
            _uiContainerState.update { UiContainerState(songInfo) }
        }
    }

    companion object {
        fun provideFactory(appContainer: AppContainer) =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return UiContainerViewModel(appContainer.songInfoRepository) as T
                }
            }
    }
}
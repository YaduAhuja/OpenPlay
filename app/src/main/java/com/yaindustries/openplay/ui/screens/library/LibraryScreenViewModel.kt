package com.yaindustries.openplay.ui.screens.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.yaindustries.openplay.OpenPlayApplication
import com.yaindustries.openplay.data.models.SongInfo
import com.yaindustries.openplay.data.services.SongInfoService
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


enum class LibraryViews {
    Playlists, Songs
}

data class LibraryScreenState(
    val libraryView: LibraryViews = LibraryViews.Playlists,
    val songs: ImmutableList<SongInfo> = persistentListOf()
)

class LibraryScreenViewModel(private val songInfoService: SongInfoService) : ViewModel() {

    private val _libraryScreenState = MutableStateFlow(LibraryScreenState())
    val libraryScreenState = _libraryScreenState.asStateFlow()

    init {
        viewModelScope.launch {
            collectSongs()
        }
    }

    fun changeLibraryView(libraryView: LibraryViews) {
        val currentView = _libraryScreenState.value.libraryView
        if (currentView == libraryView)
            return
        _libraryScreenState.update { it.copy(libraryView = libraryView) }
    }

    private suspend fun collectSongs() {
        songInfoService.getAllSongsAsFlow().stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(stopTimeoutMillis = 10000),
            persistentListOf()
        ).collect { songInfoList ->
            _libraryScreenState.getAndUpdate { it.copy(songs = songInfoList.toImmutableList()) }
        }
    }


    companion object {
        fun provideFactory() =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(
                    modelClass: Class<T>,
                    extras: CreationExtras
                ): T {
                    val application = checkNotNull(extras[APPLICATION_KEY]) as OpenPlayApplication
                    return LibraryScreenViewModel(
                        application.appContainer.songInfoService
                    ) as T
                }
            }
    }
}
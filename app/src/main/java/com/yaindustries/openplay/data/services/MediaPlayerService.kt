package com.yaindustries.openplay.data.services

import android.content.Context
import android.util.Log
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.yaindustries.openplay.data.models.PlayerInfo
import com.yaindustries.openplay.data.models.SongInfo
import com.yaindustries.openplay.utils.Utilities
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MediaPlayerService(
    context: Context,
    private val songInfoService: SongInfoService,
    private val playerInfoService: PlayerInfoService
) {
    private val player = ExoPlayer.Builder(context).build()
    private var isPrepared = false
    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private var currentSongInfo: SongInfo? = null
    private val TAG = "Media Player Service"

    init {
        Log.d(TAG, "Media Player Service Created")
//        startMediaPlayerService()
    }

    fun startMediaPlayerService() {
        coroutineScope.launch {
            collectCurrentPlayingSong()
        }
        Log.d(TAG, "Listening for changes")
    }

    private suspend fun collectCurrentPlayingSong() {
        val hotFlow = playerInfoService.getPlayerSongInfoAsFlow()
            .stateIn(
                coroutineScope,
                SharingStarted.WhileSubscribed(10000),
                null
            )

        hotFlow.onEach {
            Log.d("Media Player Service", "PlayerInfo : $it")
            it?.let { handlePlayerSongInfoChange(it) }
        }.collect()
    }

    private suspend fun handlePlayerSongInfoChange(playerInfo: PlayerInfo) {
        val songInfo = songInfoService.findSongInfoById(playerInfo.songInfoId)
        Log.d(TAG, "handlePlayerSongInfoChange: $songInfo")
        songInfo?.let {
            withContext(Dispatchers.Main) {
                if (currentSongInfo != it) {
                    player.clearMediaItems()
                    player.addMediaItem(getMediaItemFromSongInfo(it))
                }

                if (playerInfo.isPlaying)
                    play()
                else
                    player.pause()
            }

        }
        currentSongInfo = songInfo
    }

    private fun getMediaItemFromSongInfo(songInfo: SongInfo) =
        MediaItem.fromUri(Utilities.getMediaStoreContentURI(songInfo))

    private fun play() {
        if (!isPrepared) {
            player.prepare()
            isPrepared = true
        }
        player.play()
    }
}
package com.yaindustries.openplay.data.services

import com.yaindustries.openplay.data.models.PlayerInfo
import com.yaindustries.openplay.data.models.SongInfo
import com.yaindustries.openplay.data.repositories.PlayerInfoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlayerInfoService(
    private val playerInfoRepository: PlayerInfoRepository
) {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    init {
        coroutineScope.launch {
            val playerInfo = playerInfoRepository.getPlayerInfo()
            playerInfo?.let {
                playerInfoRepository.upsert(playerInfo.copy(isPlaying = false))
            }
        }
    }

    fun getPlayerSongInfoAsFlow() = playerInfoRepository.getPlayerInfoAsFlow()
    private suspend fun getPlayerSongInfo() = playerInfoRepository.getPlayerInfo()

    suspend fun changeCurrentPlayingSong(newSong: SongInfo) {
        val currentPlayerSongInfo = getPlayerSongInfo()
        if (currentPlayerSongInfo != null) {
            val newPlayerSongInfo =
                currentPlayerSongInfo.copy(
                    songInfoId = newSong.id,
                    currentTime = 0,
                    isPlaying = true
                )
            playerInfoRepository.upsert(newPlayerSongInfo)
        } else
            playerInfoRepository.upsert(PlayerInfo(songInfoId = newSong.id))
    }

    suspend fun playPauseCurrentSong() {
        val currentPlayerSongInfo = getPlayerSongInfo()
        currentPlayerSongInfo?.let {
            playerInfoRepository.upsert(it.copy(isPlaying = !it.isPlaying))
        }
    }

    suspend fun pauseCurrentSong() {
        val currentPlayerSongInfo = getPlayerSongInfo()
        currentPlayerSongInfo?.let {
            if (it.isPlaying)
                playerInfoRepository.upsert(it.copy(isPlaying = false))
        }
    }

    suspend fun playCurrentSong() {
        val currentPlayerSongInfo = getPlayerSongInfo()
        currentPlayerSongInfo?.let {
            if (!it.isPlaying)
                playerInfoRepository.upsert(it.copy(isPlaying = true))
        }
    }
}
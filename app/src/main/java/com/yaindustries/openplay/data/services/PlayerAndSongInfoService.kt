package com.yaindustries.openplay.data.services

import android.util.Log
import com.yaindustries.openplay.TransactionProvider
import com.yaindustries.openplay.data.models.PlayerInfoAndSongInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PlayerAndSongInfoService(
    private val playerInfoService: PlayerInfoService,
    private val songInfoService: SongInfoService,
    private val transactionProvider: TransactionProvider,
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
) {
    private var currentJob: Job? = null

    fun getPlayerAndSongInfoAsFlow(): Flow<PlayerInfoAndSongInfo?> {
        return flow {
            val context = currentCoroutineContext()
            playerInfoService.getPlayerSongInfoAsFlow().onEach { playerInfo ->
                currentJob?.cancel()
                if (playerInfo == null)
                    emit(null)
                else {
                    currentJob = coroutineScope.launch {
                        val currentSongInfo =
                            songInfoService.findSongInfoById(playerInfo.songInfoId)
                        songInfoService.findSongInfoByIdAsFlow(playerInfo.songInfoId)
                            .stateIn(this, SharingStarted.WhileSubscribed(0, 0), currentSongInfo)
                            .onEach { songInfo ->
                                var playerInfoAndSongInfo: PlayerInfoAndSongInfo? = null
                                if (songInfo != null)
                                    playerInfoAndSongInfo =
                                        PlayerInfoAndSongInfo(playerInfo, songInfo)
                                withContext(context) {
                                    Log.d(
                                        "PlayerAndSongInfoService",
                                        "getPlayerAndSongInfoAsFlow: $playerInfoAndSongInfo"
                                    )
                                    emit(playerInfoAndSongInfo)
                                }
                            }.collect()
                    }
                }
            }.collect()
        }
    }


//    private fun
}
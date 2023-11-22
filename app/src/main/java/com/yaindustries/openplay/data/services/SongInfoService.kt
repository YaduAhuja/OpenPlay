package com.yaindustries.openplay.data.services

import com.yaindustries.openplay.TransactionProvider
import com.yaindustries.openplay.data.models.SongInfo
import com.yaindustries.openplay.data.repositories.SongInfoRepository

class SongInfoService(
    private val transactionProvider: TransactionProvider,
    private val songInfoRepository: SongInfoRepository
) {

    fun getPlayingSongAsFlow() = songInfoRepository.getPlayingSongAsFlow()

    suspend fun changeCurrentPlayingSong(newSong: SongInfo) {
        val currentSong = songInfoRepository.getPlayingSong()
        val newCurrentSong = newSong.copy(isPlaying = true)

        if (currentSong != null) {
            transactionProvider.runWithTransaction {
                val updatedCurrentSong = currentSong.copy(isPlaying = false)
                songInfoRepository.updateSongInfo(updatedCurrentSong)
                songInfoRepository.updateSongInfo(newCurrentSong)
            }
        } else
            songInfoRepository.updateSongInfo(newCurrentSong)
    }
}
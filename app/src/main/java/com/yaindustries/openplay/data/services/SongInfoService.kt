package com.yaindustries.openplay.data.services

import com.yaindustries.openplay.data.models.SongInfo
import com.yaindustries.openplay.data.repositories.SongInfoRepository

class SongInfoService(
    private val songInfoRepository: SongInfoRepository
) {

    suspend fun findSongInfoById(id: Long) = songInfoRepository.findSongInfoById(id)

    fun findSongInfoByIdAsFlow(id: Long) = songInfoRepository.findSongInfoByIdAsFlow(id)

    fun getAllSongsAsFlow() = songInfoRepository.getAllSongsAsFlow()

    suspend fun upsert(songInfoCollection: Collection<SongInfo>) =
        songInfoRepository.upsert(songInfoCollection)

    suspend fun upsert(vararg songInfo: SongInfo) = songInfoRepository.upsert(*songInfo)

    suspend fun deleteAll() = songInfoRepository.deleteAll()
}
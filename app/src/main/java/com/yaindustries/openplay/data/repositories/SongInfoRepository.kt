package com.yaindustries.openplay.data.repositories

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.yaindustries.openplay.data.models.SongInfo
import kotlinx.coroutines.flow.Flow

@Dao
interface SongInfoRepository {
    @Query("SELECT * FROM songInfo WHERE isPlaying = 1 LIMIT 1")
    fun getPlayingSongAsFlow(): Flow<SongInfo?>

    @Query("SELECT * FROM songInfo WHERE isPlaying = 1 LIMIT 1")
    suspend fun getPlayingSong(): SongInfo?

    @Query("SELECT * FROM songInfo")
    suspend fun getAllSongs(): List<SongInfo>

    @Query("SELECT * FROM songInfo")
    fun getAllSongsAsFlow(): Flow<List<SongInfo>>

    @Upsert
    suspend fun upsert(songInfo: SongInfo)

    @Upsert
    suspend fun upsert(vararg songInfo: SongInfo)

    @Upsert
    suspend fun upsert(songInfoList: Collection<SongInfo>)

    @Delete
    suspend fun delete(songInfo: SongInfo)

    @Query("DELETE FROM SongInfo")
    suspend fun deleteAll()

    @Delete
    suspend fun deleteAll(songInfoList: Collection<SongInfo>)
}
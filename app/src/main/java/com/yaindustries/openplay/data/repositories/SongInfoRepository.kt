package com.yaindustries.openplay.data.repositories

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.yaindustries.openplay.data.models.SongInfo
import kotlinx.coroutines.flow.Flow

@Dao
interface SongInfoRepository {
    @Query("SELECT * FROM songInfo WHERE isPlaying = 1 LIMIT 1")
    fun getPlayingSongAsFlow(): Flow<SongInfo?>

    @Query("SELECT * FROM songInfo WHERE isPlaying = 1 LIMIT 1")
    suspend fun getPlayingSong(): SongInfo?

    @Query("SELECT * FROM songInfo")
    fun getAllSongs(): Flow<List<SongInfo>>

    @Insert
    suspend fun insertAll(vararg songInfo: SongInfo)

    @Update
    suspend fun updateSongInfo(songInfo: SongInfo)

    @Insert
    suspend fun insertAll(songInfoList: Collection<SongInfo>)

    @Delete
    suspend fun delete(songInfo: SongInfo)

    @Query("DELETE FROM SongInfo")
    suspend fun deleteAll()

    @Delete
    suspend fun deleteAllByCollection(songInfoList: Collection<SongInfo>)
}
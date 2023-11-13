package com.yaindustries.openplay.data.repositories

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.yaindustries.openplay.data.models.SongInfo
import kotlinx.coroutines.flow.Flow

@Dao
interface SongInfoRepository {
    @Query("SELECT * FROM songInfo WHERE isPlaying = 1 LIMIT 1")
    fun getPlayingSong(): Flow<SongInfo?>

    @Insert
    suspend fun insertAll(vararg songInfo: SongInfo)

    @Delete
    suspend fun delete(songInfo: SongInfo)
}
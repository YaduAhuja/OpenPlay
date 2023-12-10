package com.yaindustries.openplay.data.repositories

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.yaindustries.openplay.data.models.SongInfo
import kotlinx.coroutines.flow.Flow

@Dao
interface SongInfoRepository {
    @Query("SELECT * FROM SongInfo")
    suspend fun getAllSongs(): List<SongInfo>

    @Query("SELECT * FROM SongInfo")
    fun getAllSongsAsFlow(): Flow<List<SongInfo>>

    @Query("SELECT * from SongInfo where id= :id")
    suspend fun findSongInfoById(id: Long): SongInfo?

    @Query("SELECT * from SongInfo where id= :id")
    fun findSongInfoByIdAsFlow(id: Long): Flow<SongInfo?>

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
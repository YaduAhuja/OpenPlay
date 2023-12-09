package com.yaindustries.openplay.data.repositories

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.yaindustries.openplay.data.models.PlayerInfo
import kotlinx.coroutines.flow.Flow


@Dao
interface PlayerInfoRepository {

    @Query("SELECT * FROM PlayerInfo LIMIT 1")
    fun getPlayerInfoAsFlow(): Flow<PlayerInfo?>

    @Query("SELECT * FROM PlayerInfo LIMIT 1")
    suspend fun getPlayerInfo(): PlayerInfo?

    @Upsert
    suspend fun upsert(playerInfo: PlayerInfo)

    @Delete
    suspend fun delete(playerInfo: PlayerInfo)
}
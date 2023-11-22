package com.yaindustries.openplay.data.repositories

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.yaindustries.openplay.data.models.AppConfiguration
import kotlinx.coroutines.flow.Flow


@Dao
interface AppConfigurationRepository {
    @Query("SELECT * FROM AppConfiguration")
    suspend fun getAppConfig(): AppConfiguration?

    @Query("SELECT * FROM AppConfiguration")
    fun getAppConfigAsFlow(): Flow<AppConfiguration?>

    @Upsert
    suspend fun upsertAppConfig(appConfiguration: AppConfiguration)
}
package com.yaindustries.openplay.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.yaindustries.openplay.data.models.AppConfiguration
import com.yaindustries.openplay.data.models.SongInfo
import com.yaindustries.openplay.data.repositories.AppConfigurationRepository
import com.yaindustries.openplay.data.repositories.SongInfoRepository

@Database(entities = [AppConfiguration::class, SongInfo::class], version = 1, exportSchema = false)
abstract class OpenPlayRoomDatabase : RoomDatabase() {
    abstract fun songInfoRepository(): SongInfoRepository
    abstract fun appConfigRepository(): AppConfigurationRepository

    companion object {
        @Volatile
        private var INSTANCE: OpenPlayRoomDatabase? = null

        fun getDatabase(context: Context): OpenPlayRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    OpenPlayRoomDatabase::class.java,
                    "OpenPlayDB"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
package com.yaindustries.openplay

import android.content.Context
import com.yaindustries.openplay.data.OpenPlayRoomDatabase
import com.yaindustries.openplay.data.repositories.SongInfoRepository


interface AppContainer {
    val songInfoRepository: SongInfoRepository
}


class OpenPlayAppContainer(context: Context) : AppContainer {

    private val openPlayRoomDB by lazy {
        OpenPlayRoomDatabase.getDatabase(context)
    }

    override val songInfoRepository: SongInfoRepository by lazy {
        return@lazy openPlayRoomDB.songInfoRepository()
    }


}
package com.yaindustries.openplay

import android.content.Context
import com.yaindustries.openplay.data.OpenPlayRoomDatabase
import com.yaindustries.openplay.data.repositories.AppConfigurationRepository
import com.yaindustries.openplay.data.repositories.SongInfoRepository
import com.yaindustries.openplay.data.services.AppConfigurationService
import com.yaindustries.openplay.data.services.MediaStoreService
import com.yaindustries.openplay.data.services.SongInfoService


interface AppContainer {
    val transactionProvider: TransactionProvider
    val appConfigurationService: AppConfigurationService
    val mediaStoreService: MediaStoreService
    val songInfoService: SongInfoService
    val appConfigurationRepository: AppConfigurationRepository
    val songInfoRepository: SongInfoRepository
}


class OpenPlayAppContainer(context: Context) : AppContainer {

    private val openPlayRoomDB by lazy {
        OpenPlayRoomDatabase.getDatabase(context)
    }

    override val transactionProvider by lazy {
        TransactionProvider(openPlayRoomDB)
    }

    override val appConfigurationRepository by lazy {
        openPlayRoomDB.appConfigRepository()
    }

    override val songInfoRepository: SongInfoRepository by lazy {
        openPlayRoomDB.songInfoRepository()
    }

    override val appConfigurationService by lazy {
        AppConfigurationService(appConfigurationRepository)
    }

    override val mediaStoreService by lazy {
        MediaStoreService(
            context,
            transactionProvider,
            appConfigurationService,
            songInfoRepository
        )
    }

    override val songInfoService: SongInfoService by lazy {
        SongInfoService(transactionProvider, songInfoRepository)
    }
}
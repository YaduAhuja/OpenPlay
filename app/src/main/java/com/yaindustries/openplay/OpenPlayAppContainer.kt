package com.yaindustries.openplay

import android.content.Context
import com.yaindustries.openplay.data.OpenPlayRoomDatabase
import com.yaindustries.openplay.data.repositories.AppConfigurationRepository
import com.yaindustries.openplay.data.repositories.PlayerInfoRepository
import com.yaindustries.openplay.data.repositories.SongInfoRepository
import com.yaindustries.openplay.data.services.AppConfigurationService
import com.yaindustries.openplay.data.services.MediaPlayerService
import com.yaindustries.openplay.data.services.MediaStoreService
import com.yaindustries.openplay.data.services.PlayerAndSongInfoService
import com.yaindustries.openplay.data.services.PlayerInfoService
import com.yaindustries.openplay.data.services.SongInfoService


interface AppContainer {
    val transactionProvider: TransactionProvider
    val appConfigurationService: AppConfigurationService
    val mediaStoreService: MediaStoreService
    val songInfoService: SongInfoService
    val mediaPlayerService: MediaPlayerService
    val playerInfoService: PlayerInfoService
    val playerAndSongInfoService: PlayerAndSongInfoService
    val appConfigurationRepository: AppConfigurationRepository
    val songInfoRepository: SongInfoRepository
    val playerInfoRepository: PlayerInfoRepository
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

    override val songInfoRepository by lazy {
        openPlayRoomDB.songInfoRepository()
    }

    override val playerInfoRepository by lazy {
        openPlayRoomDB.playerSongInfoRepository()
    }

    override val appConfigurationService by lazy {
        AppConfigurationService(appConfigurationRepository)
    }

    override val songInfoService: SongInfoService by lazy {
        SongInfoService(songInfoRepository)
    }

    override val mediaStoreService by lazy {
        MediaStoreService(
            context,
            transactionProvider,
            appConfigurationService,
            songInfoService
        )
    }

    override val playerInfoService by lazy {
        PlayerInfoService(playerInfoRepository)
    }

    override val mediaPlayerService by lazy {
        MediaPlayerService(context, songInfoService, playerInfoService)
    }

    override val playerAndSongInfoService by lazy {
        PlayerAndSongInfoService(playerInfoService, songInfoService, transactionProvider)
    }
}
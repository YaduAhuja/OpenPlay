package com.yaindustries.openplay.data.services

import com.yaindustries.openplay.TransactionProvider

class PlayerInfoAndSongInfoService(
    private val transactionProvider: TransactionProvider,
    private val playerInfoService: PlayerInfoService,
    private val songInfoService: SongInfoService
) {

}
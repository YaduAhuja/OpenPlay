package com.yaindustries.openplay

import androidx.room.withTransaction
import com.yaindustries.openplay.data.OpenPlayRoomDatabase

class TransactionProvider(private val appDb: OpenPlayRoomDatabase) {
    suspend fun <T> runWithTransaction(block: suspend () -> T): T {
        return appDb.withTransaction(block)
    }
}
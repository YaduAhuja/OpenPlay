package com.yaindustries.openplay.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AppConfiguration(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val mediaStoreVersion: String,
    val mediaStoreGeneration: Long,
)

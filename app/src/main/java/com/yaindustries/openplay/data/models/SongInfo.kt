package com.yaindustries.openplay.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SongInfo(
    @PrimaryKey(autoGenerate = true)
    val songId: Int,
    val name: String,
    val artists: String,
    val maxTime: Int,
    val currentTime: Int,
    val isFavourite: Boolean,
    val isPlaying: Boolean
)

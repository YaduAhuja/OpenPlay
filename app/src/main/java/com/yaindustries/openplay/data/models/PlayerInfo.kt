package com.yaindustries.openplay.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PlayerInfo(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val songInfoId: Long,
    val isPlaying: Boolean = true,
    val currentTime: Int = 0,
)

package com.yaindustries.openplay.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yaindustries.openplay.data.interfaces.Info

@Entity
data class SongInfo(
    @PrimaryKey(autoGenerate = true)
    override val id: Long,
    override val name: String,
    override val artists: String,
    val album: String,
    val maxTime: Int,
    val isFavorite: Boolean = false
) : Info

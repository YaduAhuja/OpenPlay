package com.yaindustries.openplay.data.models

import androidx.room.Relation

data class PlayerInfoAndSongInfo(
    val playerInfo: PlayerInfo,
    @Relation(parentColumn = "id", entityColumn = "id")
    val songInfo: SongInfo
) {}
package com.yaindustries.openplay.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yaindustries.openplay.data.interfaces.Info
import kotlinx.collections.immutable.ImmutableList

@Entity
data class PlaylistInfo(
    @PrimaryKey(autoGenerate = true)
    override val id: Long,
    override val name: String,
    override val artists: String,
    val songs: ImmutableList<SongInfo>
) : Info

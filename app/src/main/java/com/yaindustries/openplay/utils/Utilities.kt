package com.yaindustries.openplay.utils

import android.Manifest
import android.content.ContentUris
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.provider.MediaStore
import androidx.compose.runtime.MutableState
import com.yaindustries.openplay.data.models.SongInfo
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.toImmutableMap

object Utilities {
    fun <T> updateValueByCheckingEquality(currentValue: MutableState<T>, newValue: T) {
        if (currentValue.value != newValue)
            currentValue.value = newValue
    }


    fun arePermissionsAvailable(
        context: Context,
        vararg permissions: String
    ): ImmutableMap<String, Boolean> {
        val map = mutableMapOf<String, Boolean>()
        for (permission in permissions)
            map[permission] = isPermissionAvailable(context, permission)
        return map.toImmutableMap()
    }

    fun isPermissionAvailable(context: Context, permission: String) =
        context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED

    fun isAndroidRAndUp() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.R

    fun getAudioPermissions(): Array<String> {
        return when (Build.VERSION.SDK_INT) {
            in Build.VERSION_CODES.TIRAMISU..Build.VERSION_CODES.UPSIDE_DOWN_CAKE -> arrayOf(
                Manifest.permission.READ_MEDIA_AUDIO
            )

            in Build.VERSION_CODES.Q..Build.VERSION_CODES.S_V2 -> arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
            else -> arrayOf()
        }
    }

    fun getMediaStoreVersion(context: Context) =
        MediaStore.getVersion(context, MediaStore.VOLUME_EXTERNAL_PRIMARY)

    fun getMediaStoreGeneration(context: Context): Long {
        if (isAndroidRAndUp())
            return MediaStore.getGeneration(context, MediaStore.VOLUME_EXTERNAL_PRIMARY)
        return 0L
    }

    fun getMediaStoreContentURI(songInfo: SongInfo) =
        ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, songInfo.id)
}
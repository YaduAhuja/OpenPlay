package com.yaindustries.openplay.data.services

import android.content.Context
import android.provider.MediaStore
import android.util.Log
import com.yaindustries.openplay.TransactionProvider
import com.yaindustries.openplay.data.models.AppConfiguration
import com.yaindustries.openplay.data.models.SongInfo
import com.yaindustries.openplay.utils.Utilities
import java.util.concurrent.TimeUnit

class MediaStoreService(
    private val context: Context,
    private val transactionProvider: TransactionProvider,
    private val appConfigurationService: AppConfigurationService,
    private val songInfoService: SongInfoService
) {
    private val TAG = "MediaStoreService"

    suspend fun refreshMediaStore() {
        val appConfig = appConfigurationService.getAppConfig()
        val updatedAppConfig = getUpdatedAppConfig(appConfig)

        if (!isMediaStoreChanged(appConfig)) {
            Log.d(TAG, "Media store version Same skipping media store refresh")
            return
        }
        Log.d(TAG, "Media Store Version Changed Refreshing Data")
        val songInfoList = mutableListOf<SongInfo>()
        val collection = MediaStore.Audio.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.DISPLAY_NAME,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.ALBUM
        )
        val selection = "${MediaStore.Audio.Media.DURATION} >= ?"
        val selectionArgs = arrayOf(
            TimeUnit.MILLISECONDS.convert(10, TimeUnit.SECONDS).toString()
        )

        val sortOrder = "${MediaStore.Audio.Media.DISPLAY_NAME} ASC"

        val cursor = context.contentResolver.query(
            collection,
            projection,
            selection,
            selectionArgs,
            sortOrder
        )

        cursor?.use {
            Log.d("Songs View", "$cursor ${cursor.count}")

            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
            val nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME)
            val durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)
            val artistColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
            val albumColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM)


            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val name = cursor.getString(nameColumn)
                val duration = cursor.getInt(durationColumn)
                val artist = cursor.getString(artistColumn)
                val album = cursor.getString(albumColumn)

                songInfoList.add(
                    SongInfo(
                        id,
                        name,
                        artist,
                        album,
                        duration
                    )
                )
            }
        }

        transactionProvider.runWithTransaction {
            songInfoService.deleteAll()
            songInfoService.upsert(songInfoList)
            appConfigurationService.updateAppConfig(updatedAppConfig)
        }
    }

    private fun getUpdatedAppConfig(appConfig: AppConfiguration): AppConfiguration {
        val mediaStoreVersion = MediaStore.getVersion(context, MediaStore.VOLUME_EXTERNAL_PRIMARY)
        var mediaStoreGeneration = 0L
        if (Utilities.isAndroidRAndUp())
            mediaStoreGeneration =
                MediaStore.getGeneration(context, MediaStore.VOLUME_EXTERNAL_PRIMARY)

        return appConfig.copy(
            mediaStoreVersion = mediaStoreVersion,
            mediaStoreGeneration = mediaStoreGeneration
        )
    }

    private fun isMediaStoreChanged(appConfig: AppConfiguration): Boolean {
        if (Utilities.isAndroidRAndUp())
            return appConfig.mediaStoreVersion != Utilities.getMediaStoreVersion(context) ||
                    appConfig.mediaStoreGeneration != Utilities.getMediaStoreGeneration(context)
        return true
    }
}
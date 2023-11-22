package com.yaindustries.openplay.data.services

import com.yaindustries.openplay.data.models.AppConfiguration
import com.yaindustries.openplay.data.repositories.AppConfigurationRepository

class AppConfigurationService(
    private val appConfigRepository: AppConfigurationRepository
) {

    suspend fun getAppConfig(): AppConfiguration {
        val appConfig = appConfigRepository.getAppConfig()
        if (appConfig == null) {
            val initialAppConfig = AppConfiguration(0, "", 0L)
            appConfigRepository.upsertAppConfig(initialAppConfig)
            return appConfigRepository.getAppConfig()!!
        }

        return appConfig
    }

    suspend fun updateAppConfig(appConfig: AppConfiguration) {
        appConfigRepository.upsertAppConfig(appConfig)
    }
}
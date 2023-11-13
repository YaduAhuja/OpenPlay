package com.yaindustries.openplay

import android.app.Application

class OpenPlayApplication : Application() {

    lateinit var appContainer: AppContainer

    override fun onCreate() {
        super.onCreate()
        appContainer = OpenPlayAppContainer(this)
    }
}
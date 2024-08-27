package com.example.reposlist.ui

import android.app.Application
import com.example.reposlist.plarform.AppLogger
import com.example.shareddata.logger.Logger
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ListApp : Application() {

    override fun onCreate() {
        super.onCreate()

        Logger.injectLogger(AppLogger())
    }
}
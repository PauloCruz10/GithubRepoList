package com.example.reposlist.ui

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.example.repolist.worker.schedulePeriodicRepositoriesUpdate
import com.example.reposlist.ui.plarform.AppLogger
import com.example.shareddata.logger.Logger
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class ListApp : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
    }

    override fun onCreate() {
        super.onCreate()

        Logger.injectLogger(AppLogger())
        schedulePeriodicRepositoriesUpdate(this)
    }
}
package com.example.reposlist.ui

import android.app.Application
import androidx.work.Configuration
import com.example.repolist.worker.CustomWorkerFactory
import com.example.repolist.worker.schedulePeriodicWorker
import com.example.reposlist.ui.plarform.AppLogger
import com.example.shareddata.logger.Logger
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class ListApp : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: CustomWorkerFactory

    override fun onCreate() {
        super.onCreate()
        Logger.injectLogger(AppLogger())

        schedulePeriodicWorker(this)
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
    }
}
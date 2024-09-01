package com.example.repolist.worker

import android.content.Context
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

fun schedulePeriodicWorker(appContext: Context) {
    val periodicWorkRequest = PeriodicWorkRequest.Builder(
        UpdateRepositoriesWorker::class.java,
        15, TimeUnit.MINUTES
    ).build()

    WorkManager.getInstance(appContext).enqueue(periodicWorkRequest)
}
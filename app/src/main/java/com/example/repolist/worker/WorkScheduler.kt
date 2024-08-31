package com.example.repolist.worker

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

private const val GITHUB_REPO_SCHEDULER_TAG = "UpdateReposWorkerTAG"

fun schedulePeriodicRepositoriesUpdate(context: Context) {
    val workRequest = PeriodicWorkRequestBuilder<UpdateRepositoriesWorker>(10, TimeUnit.MINUTES)
        .setConstraints(
            Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
        )
        .build()

    WorkManager.getInstance(context).enqueueUniquePeriodicWork(
        GITHUB_REPO_SCHEDULER_TAG,
        ExistingPeriodicWorkPolicy.UPDATE,
        workRequest
    )
}

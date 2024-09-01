package com.example.repolist.worker

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.example.shareddata.repository.GithubsRepository
import javax.inject.Inject

class CustomWorkerFactory @Inject constructor(private val githubsRepository: GithubsRepository) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker = UpdateRepositoriesWorker(githubsRepository, appContext, workerParameters)
}
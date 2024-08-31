package com.example.repolist.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.shareddata.logger.Logger
import com.example.shareddata.repository.GithubsRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@HiltWorker
class UpdateRepositoriesWorker @AssistedInject constructor(
    @Assisted val appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val githubsRepository: GithubsRepository,
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            try {
                Logger.d("UpdateRepositoriesWorker", "doWork")
                githubsRepository.loadRepositories()
                Result.success()
            } catch (e: Exception) {
                Result.retry()
            }
        }
    }
}
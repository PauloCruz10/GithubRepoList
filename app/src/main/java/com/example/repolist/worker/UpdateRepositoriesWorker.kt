package com.example.repolist.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.reposlist.ui.common.getRepoListConfig
import com.example.shareddata.repository.GithubsRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class UpdateRepositoriesWorker @AssistedInject constructor(
    @Assisted val githubsRepository: GithubsRepository,
    @Assisted val appContext: Context,
    @Assisted workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result {
        try {
            with(getRepoListConfig()) {
                githubsRepository.loadRepositories(
                    language,
                    sort,
                    order
                )
            }
            return Result.success()
        } catch (e: Exception) {
            return Result.failure()
        }
    }
}
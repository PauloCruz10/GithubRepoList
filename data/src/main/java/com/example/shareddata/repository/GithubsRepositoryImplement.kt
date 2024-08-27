package com.example.shareddata.repository

import com.example.network.api.RepositoriesListApi
import com.example.shareddata.common.Resource
import com.example.shareddata.db.dao.RepositoryDao
import com.example.shareddata.logger.Logger
import com.example.shareddata.mappers.mapToEntity
import com.example.shareddata.mappers.mapToLib
import com.example.shareddata.model.repositories.Repository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.random.Random

/**
 * Implementation of [AppsRepository]
 */
class GithubsRepositoryImplement @Inject constructor(private val repositoryListApi: RepositoriesListApi, private val repositoryDao: RepositoryDao) : GithubsRepository {
    /**
     * Loads the repos from the network, and save into the database.
     * The consumers will be notified when collecting to the flow provided by room engine
     */
    override suspend fun loadRepositories(): Resource<Unit> {
        return withDelay {
            try {
                val response = repositoryListApi.getAll()
                Logger.d("loadRepositories", "response=$response")
                val body = if (!response.isSuccessful) return@withDelay Resource.Failure() else response.body()
                if (body != null) {
                    val repos = body.items.mapToEntity()
                    if (repos.isNotEmpty()) {
                        repos.forEach { repo ->
                            Logger.d("loadRepositories", "repo=$repo")
                            repositoryDao.insertRepo(repo)
                        }
                        Resource.Success(Unit)
                    } else {
                        Logger.d("loadRepositories", "empty body")
                        Resource.Failure()
                    }
                } else {
                    Resource.Failure()
                }
            } catch (e: Exception) {
                println("dude e=$e")
                Logger.e("loadRepositories", "unable to load the apps", "$e")
                Resource.Failure()
            }
        }
    }

    /**
     * Gets all apps from the database with flow. Listeners will be notified on any changes
     */
    override suspend fun getRepositories(): Flow<Resource<List<Repository>>> {
        return flow {
            emit(Resource.Loading())

            emitAll(
                repositoryDao.getAllReposFlow().map { apps ->
                    Logger.d("getApps", "apps=$apps")
                    Resource.Success(apps.mapToLib())
                }
            )
        }.flowOn(Dispatchers.IO)
    }

    /**
     * Get app for a given[id] with flow
     */
    override suspend fun getRepositoryById(id: Long): Flow<Resource<Repository>> {
        return flow {
            emit(Resource.Loading())

            emitAll(
                repositoryDao.getRepoByIdFlow(id.toString()).map { app ->
                    Logger.d("getAppById", "app=$app")
                    if (app == null) Resource.Failure() else Resource.Success(app.mapToLib())
                }
            )
        }.flowOn(Dispatchers.IO)
    }
}

private suspend fun <T> withDelay(
    delayInMilSec: Long = Random.nextLong(300, 1500),
    coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO,
    task: suspend () -> T,
): T {
    return withContext(context = coroutineDispatcher) {
        delay(delayInMilSec)
        task()
    }
}

package com.example.shareddata.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.network.api.RepositoriesListApi
import com.example.shareddata.common.Resource
import com.example.shareddata.db.dao.RemoteKeyDao
import com.example.shareddata.db.dao.RepositoryDao
import com.example.shareddata.logger.Logger
import com.example.shareddata.mappers.mapToEntity
import com.example.shareddata.mappers.mapToLib
import com.example.shareddata.model.repositories.Repository
import com.example.shareddata.paging.RepositoriesRemoteMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Implementation of [AppsRepository]
 */
class GithubsRepositoryImplement @Inject constructor(
    private val repositoryListApi: RepositoriesListApi,
    private val repositoryDao: RepositoryDao,
    private val remoteKeyDao: RemoteKeyDao,
) : GithubsRepository {
    /**
     * Loads the repos from the network, and save into the database.
     * The consumers will be notified when collecting to the flow provided by room engine
     */
    override suspend fun loadRepositories(): Resource<Unit> {
        return try {
            val response = repositoryListApi.getAll(page = 1, perPage = 50)
            Logger.d("loadRepositories", "response=$response")
            val body = if (!response.isSuccessful) return Resource.Failure() else response.body()
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

    @OptIn(ExperimentalPagingApi::class)
    override fun getPagedRepositories(): Flow<PagingData<Repository>> {
        Logger.d("GithubsRepositoryImplement", "getPagedRepositories")
        return Pager(
            config = PagingConfig(
                pageSize = 100,
                prefetchDistance = 50,
                enablePlaceholders = false,
            ),
            remoteMediator = RepositoriesRemoteMediator(repositoryListApi, repositoryDao, remoteKeyDao),
            pagingSourceFactory = { repositoryDao.getAllReposFlow() }
        ).flow.map { pagingData ->
            pagingData.map { repoEntity ->
                repoEntity.mapToLib()
            }
        }
    }
}
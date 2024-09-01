package com.example.shareddata.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.network.api.RepositoriesListApi
import com.example.shareddata.common.PAGE_SIZE
import com.example.shareddata.common.PRE_FETCH_DISTANCE
import com.example.shareddata.common.REPO_REMOTE_KEY
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
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Implementation of [GithubsRepository]
 */
class GithubsRepositoryImplement @Inject constructor(
    private val repositoryListApi: RepositoriesListApi,
    private val repositoryDao: RepositoryDao,
    private val remoteKeyDao: RemoteKeyDao,
) : GithubsRepository {
    /**
     * Loads the repos from the network, and save into the database.
     * This should only be used to refresh the current content on the database
     */
    override suspend fun loadRepositories() {
        withContext(Dispatchers.IO) {
            val currentSavedPage = remoteKeyDao.getKeyByRepo(REPO_REMOTE_KEY)?.next_page ?: run {
                Logger.d("loadRepositories", "")
                return@withContext
            }

            val pages = if (currentSavedPage < 1) 0 else currentSavedPage

            for (i in 1..pages) {
                println("aqui")
                try {
                    val response = repositoryListApi.getAll(page = i, perPage = 100)
                    Logger.d("GithubsRepositoryImplement", "loadRepositories", "response=$response")
                    val body = if (response.isSuccessful) response.body() else {
                        Logger.d("GithubsRepositoryImplement", "loadRepositories", "unable to get the repositories for page=$i")
                        return@withContext
                    }

                    if (body != null) {
                        val repos = body.items.mapToEntity()
                        if (repos.isNotEmpty()) {
                            repos.forEach { repo ->
                                println("aquui2")
                                Logger.d("GithubsRepositoryImplement", "loadRepositories", "repo=$repo")
                                repositoryDao.insertRepo(repo)
                            }
                            Resource.Success(Unit)
                        } else {
                            Logger.d("GithubsRepositoryImplement", "loadRepositories", "empty body")
                            Resource.Failure()
                        }
                    }
                } catch (e: Exception) {
                    Logger.e("GithubsRepositoryImplement", "loadRepositories", "unable to load the repos, page=$i, e=$e")
                }
            }
        }
    }

    /**
     * Get app for a given[id] with flow
     */
    override suspend fun getRepositoryById(id: Long): Flow<Resource<Repository>> {
        return flow {
            emit(Resource.Loading())

            emitAll(
                repositoryDao.getRepoByIdFlow(id.toString()).map { repo ->
                    Logger.d("getRepoByIdFlow", "repo=$repo")
                    if (repo == null) Resource.Failure() else Resource.Success(repo.mapToLib())
                }
            )
        }.flowOn(Dispatchers.IO)
    }

    /**
     * Database is the single source of true, with pagination provides the capability of reacting to changes and publish on the flow to be observed by the UI
     */
    @OptIn(ExperimentalPagingApi::class)
    override fun getPagedRepositories(): Flow<PagingData<Repository>> {
        Logger.d("GithubsRepositoryImplement", "getPagedRepositories")
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                prefetchDistance = PRE_FETCH_DISTANCE,
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
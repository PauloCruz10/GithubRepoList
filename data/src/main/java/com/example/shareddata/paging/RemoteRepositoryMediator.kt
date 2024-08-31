package com.example.shareddata.paging

import com.example.network.api.RepositoriesListApi
import com.example.shareddata.db.dao.RepositoryDao
import com.example.shareddata.db.entity.RepositoryEntity

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.shareddata.common.REPO_REMOTE_KEY
import com.example.shareddata.db.dao.RemoteKeyDao
import com.example.shareddata.db.entity.RemoteKey
import com.example.shareddata.mappers.mapToEntity

@OptIn(ExperimentalPagingApi::class)
class RepositoriesRemoteMediator(
    private val api: RepositoriesListApi,
    private val dao: RepositoryDao,
    private val remoteKeyDao: RemoteKeyDao,
) : RemoteMediator<Int, RepositoryEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, RepositoryEntity>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val remoteKey = remoteKeyDao.getKeyByRepo(REPO_REMOTE_KEY) ?: return MediatorResult.Success(true)

                    if (remoteKey.next_page == null) {
                        return MediatorResult.Success(true)
                    }

                    remoteKey.next_page
                }
            }

            val response = api.getAll(
                perPage = state.config.pageSize,
                page = page
            )

            if (!response.isSuccessful) {
                throw Exception("API error: ${response.code()}")
            }

            val responseData = response.body()?.items ?: emptyList()
            val endOfPaginationReached = responseData.isEmpty()

            if (loadType == LoadType.REFRESH) {
                dao.clearAll()
            }
            val nextPage = if (responseData.isEmpty()) {
                null
            } else {
                page + 1
            }

            remoteKeyDao.insertKey(
                RemoteKey(
                    id = REPO_REMOTE_KEY,
                    next_page = nextPage,
                    last_updated = System.currentTimeMillis()
                )
            )

            dao.insertAll(responseData.mapToEntity())

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}

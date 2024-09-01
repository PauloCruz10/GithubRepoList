package com.example.repolist.test

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingConfig
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.network.api.RepositoriesListApi
import com.example.network.model.repositories.ItemsDto
import com.example.network.model.repositories.RepositoryInfoDto
import com.example.shareddata.common.REPO_REMOTE_KEY
import com.example.shareddata.db.dao.RemoteKeyDao
import com.example.shareddata.db.dao.RepositoryDao
import com.example.shareddata.db.entity.RemoteKey
import com.example.shareddata.db.entity.RepositoryEntity
import com.example.shareddata.mappers.mapToEntity
import com.example.shareddata.paging.RepositoriesRemoteMediator
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class RepositoriesRemoteMediatorTest {

    private lateinit var api: RepositoriesListApi
    private lateinit var dao: RepositoryDao
    private lateinit var remoteKeyDao: RemoteKeyDao
    private lateinit var mediator: RepositoriesRemoteMediator

    @Before
    fun setup() {
        api = mockk()
        dao = mockk(relaxed = true)
        remoteKeyDao = mockk()
        mediator = RepositoriesRemoteMediator(api, dao, remoteKeyDao)
    }

    @OptIn(ExperimentalPagingApi::class)
    @Test
    fun `test load REFRESH successfully`() = runBlocking {
        // Arrange
        val mockPagingConfig = PagingConfig(pageSize = 50)
        val pagingState = mockk<PagingState<Int, RepositoryEntity>>()
        val mockResponse = Response.success(mockResponse)
        val mockItems = mockResponse.body()?.items ?: emptyList()

        every { pagingState.config } returns mockPagingConfig

        coEvery { remoteKeyDao.insertKey(any()) } answers { Unit }

        coEvery {
            api.getAll(
                query = any(),
                sort = any(),
                order = any(),
                perPage = any(),
                page = any()
            )
        } returns mockResponse

        coEvery { (remoteKeyDao.getKeyByRepo(REPO_REMOTE_KEY)) } answers { RemoteKey("1", 1, 0L) }
        // Act
        val result = mediator.load(LoadType.APPEND, pagingState)

        // Assert
        assert(result is RemoteMediator.MediatorResult.Success)
        coVerify { remoteKeyDao.insertKey(any()) }
        coVerify { dao.insertAll(mockItems.mapToEntity()) }
    }

    @OptIn(ExperimentalPagingApi::class)
    @Test
    fun `test load APPEND with empty response`() = runBlocking {
        // Arrange
        val pagingState = mockk<PagingState<Int, RepositoryEntity>>()
        val mockPagingConfig = PagingConfig(pageSize = 50)
        val mockResponse = Response.success(mockEmptyResponse)
        every { pagingState.config } returns mockPagingConfig

        coEvery { remoteKeyDao.insertKey(any()) } answers { Unit }

        coEvery { api.getAll(perPage = any(), page = 2) } returns mockResponse
        coEvery { (remoteKeyDao.getKeyByRepo(REPO_REMOTE_KEY)) } returns RemoteKey(REPO_REMOTE_KEY, 2, System.currentTimeMillis())

        // Act
        val result = mediator.load(LoadType.APPEND, pagingState)

        // Assert
        assert(result is RemoteMediator.MediatorResult.Success)
        assert((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
        coVerify { remoteKeyDao.insertKey(any()) }
    }

    @OptIn(ExperimentalPagingApi::class)
    @Test
    fun `test load with API error`() = runBlocking {
        // Arrange
        val pagingState = mockk<PagingState<Int, RepositoryEntity>>()
        val mockResponse = Response.error<RepositoryInfoDto>(500, mockk(relaxed = true))
        val mockPagingConfig = PagingConfig(pageSize = 50)

        every { pagingState.config } returns mockPagingConfig
        coEvery { remoteKeyDao.insertKey(any()) } answers { Unit }

        coEvery { api.getAll(perPage = any(), page = any()) } returns mockResponse

        // Act
        val result = mediator.load(LoadType.REFRESH, pagingState)

        // Assert
        assert(result is RemoteMediator.MediatorResult.Success)
        assert((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    @OptIn(ExperimentalPagingApi::class)
    @Test
    fun `test load with exception`() = runBlocking {
        // Arrange
        val pagingState = mockk<PagingState<Int, RepositoryEntity>>()

        coEvery { api.getAll(perPage = any(), page = any()) } throws RuntimeException("Test Exception")

        // Act
        val result = mediator.load(LoadType.REFRESH, pagingState)

        // Assert
        assert(result is RemoteMediator.MediatorResult.Error)
    }

    private val mockResponse = RepositoryInfoDto(
        1, false, arrayListOf(
            ItemsDto(1L)
        )
    )

    private val mockEmptyResponse = RepositoryInfoDto(
        1, false, arrayListOf()
    )
}

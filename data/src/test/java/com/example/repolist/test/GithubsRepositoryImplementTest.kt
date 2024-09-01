package com.example.repolist.test

import com.example.network.api.RepositoriesListApi
import com.example.network.model.repositories.ItemsDto
import com.example.network.model.repositories.RepositoryInfoDto
import com.example.shareddata.db.dao.RemoteKeyDao
import com.example.shareddata.db.dao.RepositoryDao
import com.example.shareddata.db.entity.RemoteKey
import com.example.shareddata.db.entity.RepositoryEntity
import com.example.shareddata.logger.Logger
import com.example.shareddata.repository.GithubsRepositoryImplement
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class GithubsRepositoryImplementTest {
    private val repositoriesListApi: RepositoriesListApi = mockk(relaxed = true)
    private val repoInfoDao: RepositoryDao = mockk(relaxed = true)
    private val remoteKeyDao: RemoteKeyDao = mockk(relaxed = true)

    private val appsRepository = GithubsRepositoryImplement(repositoriesListApi, repoInfoDao, remoteKeyDao)

    @Before
    fun setup() {
        Logger.injectLogger(TestLogger)

        val mockResponse = Response.success(mockResponse)

        // Mock the API call for the suspended function
        coEvery {
            repositoriesListApi.getAll(
                query = any(),
                sort = any(),
                order = any(),
                perPage = any(),
                page = any()
            )
        } returns mockResponse
    }

    @Test
    fun `when repos list returns success, values should be stored on the database`() = runBlocking {
        val response = repositoriesListApi.getAll(
            query = "language:kotlin",
            sort = "stars",
            order = "desc",
            perPage = 100,
            page = 1
        )

        // Assert the response
        assert(response.isSuccessful)
        assert(response.body() == mockResponse)

        val entity = RepositoryEntity(1L, 1000L)
        coEvery { (repoInfoDao.insertRepo(entity)) } answers { Unit }
        coEvery { (remoteKeyDao.getKeyByRepo("repo_remote_key")) } answers { RemoteKey("1", 1, 0L) }

        appsRepository.loadRepositories()

        coVerify(exactly = 1) { repoInfoDao.insertRepo(any()) }

    }

    private val mockResponse = RepositoryInfoDto(
        1, false, arrayListOf(
            ItemsDto(1L)
        )
    )
}
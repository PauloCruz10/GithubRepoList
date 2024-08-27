import com.example.reposlist.testLogger.TestLogger
import com.example.network.api.ListAppsApi
import com.example.network.model.AllAppsInfoDto
import com.example.network.model.AllDto
import com.example.network.model.DataDto
import com.example.network.model.DatasetsDto
import com.example.network.model.InfoDto
import com.example.network.model.ListAppsDto
import com.example.network.model.ListDto
import com.example.network.model.ResponsesDto
import com.example.shareddata.common.Resource
import com.example.shareddata.db.dao.AppInfoDao
import com.example.shareddata.db.entity.AppEntity
import com.example.shareddata.logger.Logger
import com.example.shareddata.repository.GithubsRepositoryImplement
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import retrofit2.Response

class GithubsRepositoryImplementTest {
    private val listAppsApi: ListAppsApi = mock()
    private val appInfoDao: AppInfoDao = mock()

    private val appsRepository = GithubsRepositoryImplement(listAppsApi, appInfoDao)

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Logger.injectLogger(TestLogger)
    }

    @Test
    fun `loadApps should save the apps on the database when API response is successful`() = runTest {
        val mockResponse = Response.success(mockResponse)
        `when`(listAppsApi.getAll()).thenReturn(mockResponse)
        val entity = AppEntity(1, "App1")
        `when`(appInfoDao.insertApp(entity)).thenReturn(Unit)

        // When
        val result = appsRepository.loadApps()

        assert(result is Resource.Success)

        verify(appInfoDao).insertApp(entity)
    }

    @Test
    fun `loadApps should return Failure when API response is not successful`() = runTest {
        val mockResponse = Response.error<AllAppsInfoDto>(500, mock(ResponseBody::class.java))
        `when`(listAppsApi.getAll()).thenReturn(mockResponse)


        val result = appsRepository.loadApps()
        val appEntity = AppEntity(1, "App1")

        assert(result is Resource.Failure)
        verify(appInfoDao, Mockito.never()).insertApp(appEntity)
    }

    @Test
    fun `loadApps should return Failure when an exception is thrown`() = runTest {
        // Arrange
        `when`(listAppsApi.getAll()).thenThrow(RuntimeException("Test Exception"))

        val result = appsRepository.loadApps()
        val appEntity = AppEntity(1, "App1")

        assert(result is Resource.Failure)
        verify(appInfoDao, Mockito.never()).insertApp(appEntity)
    }

    private val mockResponse = AllAppsInfoDto(
        "status",
        ResponsesDto(
            ListAppsDto(
                InfoDto("status1"),
                DatasetsDto(
                    AllDto(
                        InfoDto("status2"),
                        DataDto(
                            total = 1,
                            list = listOf(ListDto(id = 1L, "App1"))
                        )
                    )
                )
            )
        )
    )
    // ... Other test cases
}
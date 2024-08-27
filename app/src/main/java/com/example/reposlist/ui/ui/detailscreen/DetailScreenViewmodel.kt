package com.example.reposlist.ui.ui.detailscreen

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reposlist.common.formatSize
import com.example.reposlist.ui.model.GithubRepoDetails
import com.example.shareddata.common.Resource
import com.example.shareddata.model.repositories.Repository
import com.example.shareddata.repository.GithubsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailScreenViewmodel @Inject constructor(savedStateHandle: SavedStateHandle, private val githubsRepository: GithubsRepository) : ViewModel() {
    val name by lazy { savedStateHandle.get<String>("name").orEmpty() }
    private val appId by lazy { savedStateHandle.get<Long>("id") ?: -1L }

    private val _showDialog = MutableStateFlow(false)
    val showDialog = _showDialog.asStateFlow()

    init {
        Log.d("DetailScreenViewmodel", "init")
        loadApp(appId)
    }

    private val _currentApp = MutableStateFlow<Resource<GithubRepoDetails>>(Resource.Loading())
    val currentApp = _currentApp.asStateFlow()

    fun loadApp(appId: Long = this.appId) {
        viewModelScope.launch(Dispatchers.IO) {
            githubsRepository.getRepositoryById(appId).collect {
                _currentApp.emit(handleResult(it))
            }
        }
    }

    fun showDialog() {
        _showDialog.tryEmit(true)
    }

    fun hideDialog() {
        _showDialog.tryEmit(false)
    }

    private fun handleResult(info: Resource<Repository>): Resource<GithubRepoDetails> {
        return when (info) {
            is Resource.Success -> Resource.Success(info.data.toGithubDetails())
            is Resource.Failure -> Resource.Failure()
            is Resource.Loading -> Resource.Loading()
        }
    }

    private fun Repository.toAppDetailItem(): List<RepositoryDetailItem> {
        return listOf(
            RepositoryDetailItem(name.toString(), AppDetailType.NAME),
            RepositoryDetailItem(stars.toString().formatSize(), AppDetailType.SIZE),
            RepositoryDetailItem(forks.toString(), AppDetailType.DOWNLOAD),
            //RepositoryDetailItem(updated.orEmpty().getFormattedDate(), AppDetailType.LAST_UPDATED),
            RepositoryDetailItem(stars.toString(), AppDetailType.RATING),
        )
    }

    private fun Repository.toGithubDetails(): GithubRepoDetails {
        Log.d("toGithubDetails", "app=$this")
        return GithubRepoDetails(name, avatarUrl, this.toAppDetailItem())
    }
}

data class RepositoryDetailItem(val detailedInfo: String, val type: AppDetailType)

enum class AppDetailType {
    NAME,
    SIZE,
    DOWNLOAD,
    LAST_UPDATED,
    RATING,
}
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

    init {
        Log.d("DetailScreenViewmodel", "init")
        loadRepository(appId)
    }

    private val _currentRepository = MutableStateFlow<Resource<GithubRepoDetails>>(Resource.Loading())
    val currentRepository = _currentRepository.asStateFlow()

    fun loadRepository(appId: Long = this.appId) {
        viewModelScope.launch(Dispatchers.IO) {
            githubsRepository.getRepositoryById(appId).collect {
                _currentRepository.emit(handleResult(it))
            }
        }
    }

    private fun handleResult(info: Resource<Repository>): Resource<GithubRepoDetails> {
        return when (info) {
            is Resource.Success -> Resource.Success(info.data.toGithubDetails())
            is Resource.Failure -> Resource.Failure()
            is Resource.Loading -> Resource.Loading()
        }
    }

    private fun Repository.toRepositoryDetailItem(): List<RepositoryDetailItem> {
        val details = mutableListOf<RepositoryDetailItem>()
        if (description.isNotEmpty()) details.add(RepositoryDetailItem(description, RepositoryDetailType.DESCRIPTION))
        details.addAll(
            listOf(
                RepositoryDetailItem(stars.toString().formatSize(), RepositoryDetailType.SIZE),
                RepositoryDetailItem(forks.toString(), RepositoryDetailType.DOWNLOAD),
                RepositoryDetailItem(stars.toString(), RepositoryDetailType.RATING),
                RepositoryDetailItem(openIssues.toString(), RepositoryDetailType.ISSUES)
            )
        )
        return details
    }

    private fun Repository.toGithubDetails(): GithubRepoDetails {
        Log.d("toGithubDetails", "app=$this")
        return GithubRepoDetails(name, ownerName, avatarUrl, url, this.toRepositoryDetailItem())
    }
}

data class RepositoryDetailItem(val detailedInfo: String, val type: RepositoryDetailType)

enum class RepositoryDetailType {
    NAME,
    SIZE,
    DOWNLOAD,
    LAST_UPDATED,
    RATING,
    DESCRIPTION,
    ISSUES,
}
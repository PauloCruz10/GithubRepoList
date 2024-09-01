package com.example.reposlist.ui.ui.homescreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.reposlist.ui.common.getRepoListConfig
import com.example.shareddata.model.repositories.Repository
import com.example.shareddata.repository.GithubsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val githubRepos: GithubsRepository) : ViewModel() {
    private val reposConfig = getRepoListConfig()
    val pagedRepositories: Flow<PagingData<Repository>> = githubRepos.getPagedRepositories(
        reposConfig.language,
        reposConfig.sort,
        reposConfig.order,
    )
        .cachedIn(viewModelScope)
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    init {
        Log.d("HomeViewModel", "init")
    }

    /**
     * Refresh all apps, this should only be used on error scenarios or to refresh the repositories
     */
    fun loadRepositories() {
        viewModelScope.launch(Dispatchers.Default) {
            val configs =
                githubRepos.loadRepositories(
                    reposConfig.language,
                    reposConfig.sort,
                    reposConfig.order,
                )
        }
    }
}

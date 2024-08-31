package com.example.reposlist.ui.ui.homescreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.shareddata.model.repositories.Repository
import com.example.shareddata.repository.GithubsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val githubRepos: GithubsRepository) : ViewModel() {

    val pagedRepositories: Flow<PagingData<Repository>> = githubRepos.getPagedRepositories()
        .cachedIn(viewModelScope)
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    init {
        Log.d("HomeViewModel", "init")
        /*viewModelScope.launch(Dispatchers.Default) {
            githubRepos.loadRepositories()
        }*/
    }

    fun loadApps() {
        /*viewModelScope.launch(Dispatchers.Default) {
            githubRepos.loadRepositories()
        }*/
    }
}

package com.example.reposlist.ui.ui.homescreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
class HomeViewModel @Inject constructor(private val githubRepos: GithubsRepository) : ViewModel() {
    private val _listAppApps = MutableStateFlow<Resource<List<Repository>>>(Resource.Loading())
    val listAppApps = _listAppApps.asStateFlow()

    init {
        Log.d("HomeViewModel", "init")
        viewModelScope.launch(Dispatchers.Default) {
            githubRepos.loadRepositories()
        }

        viewModelScope.launch(Dispatchers.Default) {
            githubRepos.getRepositories().collect {
               _listAppApps.emit(it)
           }
        }
    }

    fun loadApps() {
        viewModelScope.launch(Dispatchers.Default) {
            githubRepos.loadRepositories()
        }
    }
}

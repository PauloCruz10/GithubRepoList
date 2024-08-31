package com.example.shareddata.repository

import androidx.paging.PagingData
import com.example.shareddata.common.Resource
import com.example.shareddata.model.repositories.Repository
import kotlinx.coroutines.flow.Flow

/**
 * Defines the Apps repository actions
 */
interface GithubsRepository {
    /**
     * Loads all the apps
     */
    suspend fun loadRepositories(): Resource<Unit>


    /*/**
     * Get all the apps hold in an [Flow]
     */
    suspend fun getRepositories(): Flow<Resource<List<Repository>>>*/

    /**
     * Get app by [id] on [Flow]
     */
    suspend fun getRepositoryById(id: Long): Flow<Resource<Repository>>

    fun getPagedRepositories(): Flow<PagingData<Repository>>
}
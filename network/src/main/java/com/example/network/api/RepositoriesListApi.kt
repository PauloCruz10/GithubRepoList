package com.example.network.api

import com.example.network.model.repositories.RepositoryInfoDto
import retrofit2.Response
import retrofit2.http.GET

interface RepositoriesListApi {
    @GET("repositories?q=tetris+language:kotlin&sort=stars&order=desc")
    suspend fun getAll(): Response<RepositoryInfoDto>
}
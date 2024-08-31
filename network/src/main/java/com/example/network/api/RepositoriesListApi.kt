package com.example.network.api

import com.example.network.model.repositories.RepositoryInfoDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RepositoriesListApi {
    @GET("repositories")
    suspend fun getAll(
        @Query("q") query: String = "language:kotlin",
        @Query("sort") sort: String = "stars",
        @Query("order") order: String = "desc",
        @Query("per_page") perPage: Int,
        @Query("page") page: Int,
        ): Response<RepositoryInfoDto>
}
package com.example.network

import com.example.network.api.RepositoriesListApi
import com.example.network.interceptor.HeaderInterceptor
import com.example.network.model.repositories.values.GITHUB_REPO_BASE_URL
import dagger.Provides
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    fun provideApiService(retrofit: Retrofit): RepositoriesListApi {
        return retrofit.create(RepositoriesListApi::class.java)
    }

    @Provides
    fun provideRetrofit(): Retrofit {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(HeaderInterceptor())
            .build()
        return Retrofit.Builder()
            .baseUrl(GITHUB_REPO_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().build()
    }
}
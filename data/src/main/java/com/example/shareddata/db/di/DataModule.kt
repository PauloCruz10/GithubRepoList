package com.example.shareddata.db.di

import android.content.Context
import androidx.room.Room
import com.example.network.api.RepositoriesListApi
import com.example.shareddata.db.dao.RemoteKeyDao
import com.example.shareddata.db.dao.RepositoryDao
import com.example.shareddata.db.database.AppDatabase
import com.example.shareddata.repository.GithubsRepository
import com.example.shareddata.repository.GithubsRepositoryImplement
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
        "test_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideRepoInfoInfoDao(db: AppDatabase): RepositoryDao {
        return db.repoInfoDao()
    }

    @Provides
    @Singleton
    fun provideRemoteKeyDao(db: AppDatabase): RemoteKeyDao {
        return db.remoteKeyDao()
    }

    @Provides
    @Singleton
    fun provideAppsRepository(apiService: RepositoriesListApi, repoInfoDao: RepositoryDao, remoteKeyDao: RemoteKeyDao): GithubsRepository {
        return GithubsRepositoryImplement(apiService, repoInfoDao, remoteKeyDao)
    }
}
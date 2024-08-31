package com.example.shareddata.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.shareddata.db.entity.RepositoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RepositoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepo(appEntity: RepositoryEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(appEntities: List<RepositoryEntity>)

    @Query("SELECT * FROM repositories order by stars DESC")
    fun getAllReposFlow(): PagingSource<Int, RepositoryEntity>

    @Query("SELECT * FROM repositories WHERE id = :repoId")
    fun getRepoByIdFlow(repoId: String): Flow<RepositoryEntity?>

    @Query("DELETE FROM repositories")
    suspend fun clearAll()
}
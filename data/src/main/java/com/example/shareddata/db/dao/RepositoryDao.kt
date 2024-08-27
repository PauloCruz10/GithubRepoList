package com.example.shareddata.db.dao

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

    @Query("SELECT * FROM repositories")
    fun getAllReposFlow(): Flow<List<RepositoryEntity>>

    @Query("SELECT * FROM repositories WHERE id = :repoId")
    fun getRepoByIdFlow(repoId: String): Flow<RepositoryEntity?>
}
package com.example.shareddata.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.shareddata.db.entity.RemoteKey

@Dao
interface RemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKeys(keys: List<RemoteKey>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertKey(key: RemoteKey)

    @Query("select * from repo_remote_keys where id=:key")
    suspend fun getKeyByRepo(key: String): RemoteKey?

    @Query("delete from repo_remote_keys")
    suspend fun clearKeys()
}
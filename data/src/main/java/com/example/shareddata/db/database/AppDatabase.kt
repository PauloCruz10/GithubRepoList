package com.example.shareddata.db.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.shareddata.db.converters.Converters
import com.example.shareddata.db.dao.RepositoryDao
import com.example.shareddata.db.entity.RepositoryEntity

@Database(entities = [RepositoryEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun repoInfoDao(): RepositoryDao
}
package com.example.shareddata.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "repositories")
data class RepositoryEntity(
    @PrimaryKey val id: Long = 0, // Set default value to 0
    val name: String? = null,
    val fullName: String? = null,
    val ownerName: String? = null,
    val private: Boolean? = false,
    val avatarUrl: String? = null,
    val language: String? = null,
    val description: String? = null,
    val url: String? = null,
    val forks: Long? = null,
    val stars: Long? = null,
)
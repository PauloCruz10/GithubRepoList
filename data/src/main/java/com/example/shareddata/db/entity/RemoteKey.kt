package com.example.shareddata.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "repo_remote_keys"
)
data class RemoteKey(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val next_page: Int?,
    val last_updated: Long
)
package com.example.shareddata.model.repositories

data class Repository(
    val id: Long = -1,
    val name: String = "",
    val fullName: String = "",
    val ownerName: String = "",
    val private: Boolean? = false,
    val avatarUrl: String = "",
    val language: String = "",
    val description: String = "",
    val url: String = "",
    val forks: Long = -1,
    val stars: Long = -1,
)
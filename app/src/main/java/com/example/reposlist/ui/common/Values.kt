package com.example.reposlist.ui.common

fun getRepoListConfig() = RepoListConfig("language:kotlin", "starts", "desc")

data class RepoListConfig(
    val language: String,
    val sort: String,
    val order: String,
)
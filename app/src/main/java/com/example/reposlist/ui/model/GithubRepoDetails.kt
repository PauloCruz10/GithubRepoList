package com.example.reposlist.ui.model

import com.example.reposlist.ui.ui.detailscreen.RepositoryDetailItem

data class GithubRepoDetails(
    val name: String,
    val ownerName: String,
    val image: String,
    val url: String,
    val details: List<RepositoryDetailItem>,
)
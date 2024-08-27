package com.example.reposlist.ui.model

import com.example.reposlist.ui.ui.detailscreen.RepositoryDetailItem

data class GithubRepoDetails(
    val name: String,
    val image: String,
    val details: List<RepositoryDetailItem>,
)
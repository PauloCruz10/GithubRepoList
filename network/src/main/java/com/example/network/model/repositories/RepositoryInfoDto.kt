package com.example.network.model.repositories

import com.google.gson.annotations.SerializedName

data class RepositoryInfoDto(
    @SerializedName("total_count") var totalCount: Int? = null,
    @SerializedName("incomplete_results") var incompleteResults: Boolean? = null,
    @SerializedName("items") var items: ArrayList<ItemsDto> = arrayListOf(),
)
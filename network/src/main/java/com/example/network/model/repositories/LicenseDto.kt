package com.example.network.model.repositories

import com.google.gson.annotations.SerializedName

data class LicenseDto(
    @SerializedName("key") var key: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("url") var url: String? = null,
    @SerializedName("spdx_id") var spdxId: String? = null,
    @SerializedName("node_id") var nodeId: String? = null,
    @SerializedName("html_url") var htmlUrl: String? = null,
)
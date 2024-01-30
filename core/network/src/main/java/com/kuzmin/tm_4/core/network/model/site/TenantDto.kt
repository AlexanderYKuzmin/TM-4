package com.kuzmin.tm_4.core.network.model.site

import com.google.gson.annotations.SerializedName

data class TenantDto(
    @SerializedName("id")
    val id: Long,

    @SerializedName("name")
    val name: String,

    @SerializedName("tenant_logo")
    val logo: String
)

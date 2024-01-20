package com.kuzmin.tm_4.core.network.model

import com.google.gson.annotations.SerializedName

data class SiteSimpleDto(
    @SerializedName("id")
    val id: Long,

    val name: String,

    val description: String,

    val tenant: TenantDto,

    val address: AddressDto,

    val constructionsSimple: List<ConstructionSimpleDto>? = null
)

package com.kuzmin.tm_4.core.network.model.preview

import com.google.gson.annotations.SerializedName
import com.kuzmin.tm_4.core.network.model.site.TenantDto

data class SiteSampleDto(
    @SerializedName("id")
    val id: Long,

    @SerializedName("uuid")
    val uuid: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("site_type")
    val type: Int,

    @SerializedName("site_type_description")
    val typeDescription: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("site_photo")
    val photoUrl: String,

    @SerializedName("site_photo_dimensions")
    val photoDimension: String,

    @SerializedName("tenant")
    val tenant: TenantDto,

    @SerializedName("latitude")
    val latitude: Double,

    @SerializedName("longitude")
    val longitude: Double,

    @SerializedName("address")
    val address: AddressSampleDto,

    @SerializedName("site_constructions")
    val constructionsSample: List<ConstructionSampleDto>? = null
)
package com.kuzmin.tm_4.core.network.model

import com.google.gson.annotations.SerializedName

data class ConstructionDto(
    @SerializedName("id")
    val id: Long,

    @SerializedName("uuid")
    val uuid: String,

    /*@SerializedName("site_uuid")
    val siteUuid: String,*/

    @SerializedName("site_id")
    val siteId: Long,

    @SerializedName("num_of_sections")
    val numOfSections: Int,

    @SerializedName("height_mm")
    val height: Int,

    @SerializedName("construction_type")
    val type: String,

    @SerializedName("config")
    val config: String,

    @SerializedName("levels")
    val measureLevels: Int
)
package com.kuzmin.tm_4.core.network.model

import com.google.gson.annotations.SerializedName

data class ConstructionSimpleDto(
    @SerializedName("construction_type")
    val constructionType: String,

    val config: String,

    @SerializedName("height_mm")
    val heightMm: Int
)

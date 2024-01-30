package com.kuzmin.tm_4.core.network.model.preview

import com.google.gson.annotations.SerializedName

data class ConstructionSampleDto(
    @SerializedName("construction_type")
    val constructionType: String,

    val config: String,

    @SerializedName("height_mm")
    val heightMm: Int,

    @SerializedName("creation_date")
    val creationDate: String,        //yyyy-MM-dd

    @SerializedName("completed_date")
    val completedDate: String       //yyyy-MM-dd
)

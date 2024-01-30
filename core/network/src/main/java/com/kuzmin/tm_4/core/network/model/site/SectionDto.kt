package com.kuzmin.tm_4.core.network.model.site

import com.google.gson.annotations.SerializedName

data class SectionDto(
    @SerializedName("id")
    val id: Long,

    @SerializedName("uuid")
    val uuid: String,

    @SerializedName("construction_id")
    val constructionId: Long,

    @SerializedName("number")
    val number: Int,

    @SerializedName("w_bottom")
    val wBottom: Int,

    @SerializedName("w_top")
    val wTop: Int,

    @SerializedName("height")
    val height: Int,

    @SerializedName("level")
    val level: Int,         // probably delete

    @SerializedName("c_date")
    val cDate: String,

    @SerializedName("status")
    val status: String,

    @SerializedName("construction_uuid")
    val constructionUuid: String
)
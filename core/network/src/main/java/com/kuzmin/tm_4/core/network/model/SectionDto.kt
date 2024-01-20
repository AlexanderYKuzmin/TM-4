package com.kuzmin.tm_4.core.network.model

import com.google.gson.annotations.SerializedName

data class SectionDto(
    @SerializedName("id")
    val id: Long,

    @SerializedName("uuid")
    val uuid: String,

    @SerializedName("number")
    val number: Int,

    @SerializedName("w_bottom")
    val wBottom: Int,

    @SerializedName("w_top")
    val wTop: Int,

    @SerializedName("height")
    val height: Int,

    @SerializedName("level")
    val level: Int
)
package com.kuzmin.tm_4.core.network.model.site

import com.google.gson.annotations.SerializedName

data class LevelDto(
    @SerializedName("id")
    val id: Long,

    @SerializedName("number")
    val number: Int,

    @SerializedName("position_of_sec")
    val position: String,

    @SerializedName("altitude")
    val altitude: Int
)
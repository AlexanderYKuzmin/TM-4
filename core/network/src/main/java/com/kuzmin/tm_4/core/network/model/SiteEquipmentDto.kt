package com.kuzmin.tm_3.data.network.model

import com.google.gson.annotations.SerializedName

data class SiteEquipmentDto(
    @SerializedName("equipment_id")
    val id: Long,

    @SerializedName("equipment_uuid")
    val uuid: String,

    @SerializedName("type")
    val type: String,

    @SerializedName("name")
    val name: String
)
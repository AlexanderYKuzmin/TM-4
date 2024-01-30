package com.kuzmin.tm_4.core.network.model.site

import com.google.gson.annotations.SerializedName

data class GroupDto(
    @SerializedName("id")
    val id: Long,

    @SerializedName("uuid")
    val uuid: String,

    @SerializedName("measurement_construction_id")
    val measurementConstructionId: Long,

    @SerializedName("group_num")
    val groupNum: Int,

    @SerializedName("azimuth")
    val azimuth: Int,

    @SerializedName("theo_distance")
    val theoDistance: Int,

    @SerializedName("theo_height")
    val theoHeight: Int,

    @SerializedName("c_date")
    val cDate: String, // 2024-01-11 10:16:29

    @SerializedName("measurement_construction_uuid")
    val measurementConstructionUuid: String,
)
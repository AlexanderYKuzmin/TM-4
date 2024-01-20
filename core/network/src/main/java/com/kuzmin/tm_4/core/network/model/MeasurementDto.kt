package com.kuzmin.tm_4.core.network.model

import com.google.gson.annotations.SerializedName

data class MeasurementDto(
    @SerializedName("id")
    val id: Long,

    @SerializedName("uuid")
    val uuid: String,

    @SerializedName("measurement_group_uuid")
    val measurementGroupUuid: String,

    @SerializedName("measurement_group_id")
    val measurementGroupId: Long,

    @SerializedName("level")
    val level: Int,

    @SerializedName("left_angle_cl")
    val leftAngleCl: Double,

    @SerializedName("left_angle_cr")
    val leftAngleCr: Double,

    @SerializedName("right_angle_cl")
    val rightAngleCl: Double,

    @SerializedName("right_angle_cr")
    val rightAngleCr: Double,
)

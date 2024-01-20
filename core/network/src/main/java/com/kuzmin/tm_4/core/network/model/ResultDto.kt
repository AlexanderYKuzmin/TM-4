package com.kuzmin.tm_4.core.network.model

import com.google.gson.annotations.SerializedName

data class ResultDto(
    @SerializedName("id")
    val id: Long,

    @SerializedName("measurement_id")
    val measurementId: Long,

    @SerializedName("measurement_group_id")
    val measurementGroupId: Long,

    @SerializedName("uuid")
    val uuid: String,

    @SerializedName("measurement_uuid")
    val measurementUuid: String,

    @SerializedName("measurement_group_uuid")
    val measurementGroupUuid: String,

    @SerializedName("average_cl")
    val averageCl: Double,

    @SerializedName("average_cr")
    val averageCr: Double,

    @SerializedName("average_cl_cr")
    val averageClCr: Double,

    @SerializedName("shift_deg")
    val shiftDeg: Double,

    @SerializedName("shift_mm")
    val shiftMm: Int,

    @SerializedName("tan_alpha")
    val tanAlpha: Double,

    @SerializedName("dict_to_measure_level")
    val distToMeasureLevel: Int,

    @SerializedName("dist_delta")
    val distDelta: Int,

    @SerializedName("beta_average_left")
    val betaAverageLeft: Double,

    @SerializedName("beta_average_right")
    val betaAverageRight: Double,

    @SerializedName("beta_i")
    val betI: Double,

    @SerializedName("beta_delta")
    val betaDelta: Int,
)
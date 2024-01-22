package com.kuzmin.tm_4.core.network.model

import com.google.gson.annotations.SerializedName

data class MeasurementConstructionDto(
    @SerializedName("id")
    val id: Long,

    @SerializedName("uuid")
    val uuid: String,

    @SerializedName("construction_uuid")
    val constructionUuid: String,

    @SerializedName("construction_id")
    val constructionId: Long,

    @SerializedName("measurement_name")
    val measurementName: String?,

    @SerializedName("creator")
    val creator: Long,

    @SerializedName("start_level")
    val startLevel: Int,

    @SerializedName("creation_date")
    val creationDate: String,

    @SerializedName("completed_date")
    val completedDate: String,

    @SerializedName("is_completed")
    val isCompleted: Boolean,

    @SerializedName("employee")
    val employee: Long
)
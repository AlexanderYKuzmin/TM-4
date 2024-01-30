package com.kuzmin.tm_4.feature.sites.domain.model.sites

import java.util.Date

data class MeasurementConstruction(

    val uuid: String,

    val measurementName: String?,

    val creator: Long,

    val startLevel: Int,

    val creationDate: Date,

    val completedDate: Date,

    val isCompleted: Boolean,

    val employee: Long,

    val constructionUuid: String,

    val employeeName: String,

    val creatorName: String
)

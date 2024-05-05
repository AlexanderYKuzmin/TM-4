package com.kuzmin.tm_4.feature.api.model.site

import com.kuzmin.tm_4.feature.api.model.McLevelInfo
import java.util.Date

data class MeasurementConstruction(

    val uuid: String,

    val measurementName: String?,

    val creatorUuid: String,

    val startLevel: Int,

    val creationDate: Date,

    val completedDate: Date?,

    val isCompleted: Boolean,

    val employeeUuid: String,

    val constructionUuid: String,

    val employeeName: String,

    val creatorName: String,

    val isServiceable: Boolean,

    val levelsInfo: List<McLevelInfo>? = null,

)

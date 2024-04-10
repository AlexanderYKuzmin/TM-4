package com.kuzmin.tm_4.feature.sites.domain.model.sites

import java.util.Date

data class Photo (

    val uuid: String,

    val name: String,

    val date: Date, //nullable or not need to be resolved

    val url: String,

    val urlThumbnail: String,

    val employeeId: Long,

    val employeeName: String,

    val dimensionXPx: Int,

    val dimensionYPx: Int,

    val thumbnailDimXPx: Int,

    val thumbnailDimYPx: Int
)
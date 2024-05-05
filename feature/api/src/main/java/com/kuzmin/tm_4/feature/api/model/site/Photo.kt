package com.kuzmin.tm_4.feature.api.model.site

import java.util.Date

data class Photo (

    val uuid: String? = null,

    val name: String? = null,

    val date: Date? = null, //nullable or not need to be resolved

    val url: String,

    val urlThumbnail: String? = null,

    val employeeUuid: Long? = null,

    val employeeName: String? = null,

    val dimensionXPx: Int? = null,

    val dimensionYPx: Int? = null,

    val thumbnailDimXPx: Int? = null,

    val thumbnailDimYPx: Int? = null
)
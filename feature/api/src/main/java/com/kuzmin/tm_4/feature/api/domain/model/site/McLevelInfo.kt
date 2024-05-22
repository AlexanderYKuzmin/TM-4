package com.kuzmin.tm_4.feature.api.domain.model.site

import java.util.UUID

data class McLevelInfo(
    val uuid: String,

    val levelNum: Int,

    val shift: Int,

    val isServiceable: Boolean,

    val altitude: Int,

    val mcUuid: String,

    val sUuid: String
)

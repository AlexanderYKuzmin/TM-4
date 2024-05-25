package com.kuzmin.tm_4.feature.report.domain.model

data class ProfileTableData(
    val altitude: String,

    val left: Double,

    val right: Double,

    val average: Double,

    val shift: Int,

    val isExceeded: Boolean
)
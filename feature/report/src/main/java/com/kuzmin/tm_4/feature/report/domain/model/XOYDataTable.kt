package com.kuzmin.tm_4.feature.report.domain.model

data class XOYDataTable(
    val altitude: String,

    val shiftX: Int,

    val shiftY: Int,

    val shiftResult: Int,

    val isExceeded: Boolean
)
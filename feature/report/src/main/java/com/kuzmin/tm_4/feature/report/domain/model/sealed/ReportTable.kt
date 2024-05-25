package com.kuzmin.tm_4.feature.report.domain.model.sealed

sealed class ReportTable {
    class ProfileTableData(
        val altitude: String,

        val left: Double,

        val right: Double,

        val average: Double,

        val shift: Int,

        val isExceeded: Boolean
    ) : ReportTable()

    class XOYTableData(
        val altitude: String,

        val shiftX: Int,

        val shiftY: Int,

        val shiftResult: Int,

        val isExceeded: Boolean
    ) : ReportTable()
}
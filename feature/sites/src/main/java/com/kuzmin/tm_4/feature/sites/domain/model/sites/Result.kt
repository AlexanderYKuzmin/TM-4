package com.kuzmin.tm_4.feature.sites.domain.model.sites

data class Result(

    val uuid: String,

    val level: Int,

    val sectionUuid: String,

    val averageCl: Double,

    val averageCr: Double,

    val averageClCr: Double,

    val shiftDeg: Double,

    val shiftMm: Int,

    val tanAlpha: Double,

    val distToMeasureLevel: Int,

    val distDelta: Int,

    val betaAverageLeft: Double,

    val betaAverageRight: Double,

    val betI: Double,

    val betaDelta: Int,

    val measurementUuid: String,

    val measurementGroupUuid: String,
)

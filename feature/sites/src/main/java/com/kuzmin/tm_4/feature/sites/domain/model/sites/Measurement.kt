package com.kuzmin.tm_4.feature.sites.domain.model.sites

data class Measurement(

    val uuid: String,

    val level: Int,

    val leftAngleCl: Double,

    val leftAngleCr: Double,

    val rightAngleCr: Double,

    val rightAngleCl: Double,

    val measurementGroupUuid: String
)

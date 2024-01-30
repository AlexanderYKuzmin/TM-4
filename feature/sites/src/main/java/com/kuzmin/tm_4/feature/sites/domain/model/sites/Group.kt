package com.kuzmin.tm_4.feature.sites.domain.model.sites

data class Group(

    val uuid: String,

    val groupNum: Int,

    val azimuth: Int,

    val theoDistance: Int,

    val theoHeight: Int,

    val measurementConstructionUuid: String,
)

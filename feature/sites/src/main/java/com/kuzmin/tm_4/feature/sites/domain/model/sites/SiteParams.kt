package com.kuzmin.tm_4.feature.sites.domain.model.sites

data class SiteParams(
    val remoteId: Long,

    val name: String,

    val siteUuid: String,

    val description: String = "",

    val latitude: Double = 0.0,

    val longitude: Double = 0.0,

    val siteType: Int = 0,

    val siteTypeDescription: String = ""
)

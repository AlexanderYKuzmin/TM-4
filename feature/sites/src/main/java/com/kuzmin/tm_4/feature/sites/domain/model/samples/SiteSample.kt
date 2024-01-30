package com.kuzmin.tm_4.feature.sites.domain.model.samples

import com.kuzmin.tm_4.feature.sites.domain.model.Tenant

data class SiteSample(

    val uuid: String,

    val name: String,

    val type: Int,

    val typeDescription: String,

    val description: String,

    val photoUrl: String,

    val photoDimension: String,

    val tenant: Tenant,

    val latitude: Double,

    val longitude: Double,

    val address: AddressSample,

    val constructionsSample: List<ConstructionSample>? = null
    //val employee: String,
    //val completed: Boolean,
    //val approved: Boolean,
)
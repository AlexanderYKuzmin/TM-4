package com.kuzmin.tm_4.feature.api.domain.model.site_related_model.site.sample

import com.kuzmin.tm_4.feature.api.domain.model.site_related_model.site.Tenant

data class SiteSample(
    val uuid: String,

    val name: String,

    val type: Int,

    val typeDescription: String,

    val description: String,

    val photoUrl: String?,

    val photoDimension: String?,

    val tenant: Tenant,

    val latitude: Double,

    val longitude: Double,

    val address: AddressSample,

    val constructionsSample: List<ConstructionSample>? = null,

    //val employee: String,
    //val completed: Boolean,
    //val approved: Boolean,
) {
    var isChosen = false

    fun switch() {
        isChosen = !isChosen
    }
}
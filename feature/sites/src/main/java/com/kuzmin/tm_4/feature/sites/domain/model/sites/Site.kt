package com.kuzmin.tm_4.feature.sites.domain.model.sites

import com.kuzmin.tm_4.feature.sites.domain.model.Tenant

data class Site(
    val siteParams: SiteParams,

    val tenant: Tenant,

    val address: Address,

    val photos: List<Photo>? = null,

    val siteEquipments: List<SiteEquipment>? = null,

    val constructions: List<Construction>,

    val constructionsLevels: List<Level>? = null,

    val constructionsSections: List<Section>,

    val measurementsConstructions: List<MeasurementConstruction>,

    val measurementsGroupsDto: List<Group>,

    val measurements: List<Measurement>,

    val results: List<Result>
)

package com.kuzmin.tm_4.feature.sites.domain.model.sites

import com.kuzmin.tm_4.feature.sites.domain.model.Tenant

data class Site(
    val siteParams: SiteParams,

    val tenant: Tenant,

    val address: Address,

    val photos: List<Photo>,

    val siteEquipments: List<SiteEquipment>,

    val constructions: List<Construction>,

    val constructionsLevels: List<Level>? = null,

    val constructionsSections: List<Section>,

    val measurementsConstructions: List<MeasurementConstruction>,

    val measurementsGroups: List<Group>,

    val measurements: List<Measurement>,

    val results: List<Result>
)

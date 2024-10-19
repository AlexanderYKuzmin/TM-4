package com.kuzmin.tm_4.feature.api.domain.model.site_related_model.site


data class Site(
    val siteParams: SiteParams,

    val tenant: Tenant,

    val address: Address,

    val photos: List<Photo>,

    val siteEquipments: List<SiteEquipment>,

    val constructions: List<Construction>,

    val constructionsLevels: List<Level>? = null,

    val constructionsSections: List<Section>? = null,

    val measurementsConstructions: List<MeasurementConstruction>? = null,

    val measurementsGroups: List<Group>? = null,

    val measurements: List<Measurement>? = null,

    val results: List<Result>? = null,

    val levelsInfo: List<McLevelInfo>? = null
)

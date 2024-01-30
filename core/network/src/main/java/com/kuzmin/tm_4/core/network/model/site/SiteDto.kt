package com.kuzmin.tm_4.core.network.model.site

import com.google.gson.annotations.SerializedName

data class SiteDto(
    @SerializedName("site_params")
    val siteParamsDto: SiteParamsDto,

    @SerializedName("tenant")
    val tenantDto: TenantDto,

    @SerializedName("address")
    val addressDto: AddressDto,

    @SerializedName("photos")
    val photosDto: List<PhotoDto>,

    @SerializedName("equipments")
    val siteEquipmentsDto: List<SiteEquipmentDto>,

    @SerializedName("site_constructions")
    val constructionsDto: List<ConstructionDto>,

    @SerializedName("constructions_levels")
    val constructionsLevelsDto: List<LevelDto>,

    @SerializedName("constructions_sections")
    val constructionsSectionsDto: List<SectionDto>,

    @SerializedName("measurements_constructions")
    val measurementsConstructionsDto: List<MeasurementConstructionDto>,

    @SerializedName("measurements_groups")
    val measurementsGroupsDto: List<GroupDto>,

    @SerializedName("measurements")
    val measurementsDto: List<MeasurementDto>,

    @SerializedName("results")
    val resultsDto: List<ResultDto>
)
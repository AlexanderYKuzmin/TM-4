package com.kuzmin.tm_4.core.network.model

import com.google.gson.annotations.SerializedName
import com.kuzmin.tm_3.data.network.model.MeasurementConstructionDto
import com.kuzmin.tm_3.data.network.model.SiteEquipmentDto
import com.kuzmin.tm_3.data.network.model.TenantDto

data class SiteDto(
    @SerializedName("site_id")
    val id: Long,

    @SerializedName("name")
    val name: String,

    @SerializedName("site_uuid")
    val uuid: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("tenant")
    val tenant: TenantDto,

    @SerializedName("latitude")
    val latitude: Double,

    @SerializedName("longitude")
    val longitude: Double,

    @SerializedName("site_type")
    val type: Int,

    @SerializedName("site_type_description")
    val typeDescription: String,

    @SerializedName("address")
    val address: AddressDto,

    @SerializedName("photos")
    val photos: List<PhotoDto>,

    @SerializedName("equipments")
    val siteEquipments: List<SiteEquipmentDto>,

    @SerializedName("constructions")
    val constructions: List<ConstructionDto>,

    @SerializedName("constructions_levels")
    val constructionsLevels: List<LevelDto>,

    @SerializedName("constructions_sections")
    val constructionsSections: List<SectionDto>,

    @SerializedName("measurements_constructions")
    val measurementsConstructions: List<MeasurementConstructionDto>,

    @SerializedName("measurements_groups")
    val measurementsGroups: List<GroupDto>,

    @SerializedName("measurements")
    val measurements: List<MeasurementDto>,

    @SerializedName("results")
    val results: List<ResultDto>

    /*val configuration: SiteConfigurationDto,

    val reports: List<ReportDto>,*/
)
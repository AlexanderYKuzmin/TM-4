package com.kuzmin.tm_4.data.remote.mapper

import android.util.Log
import com.kuzmin.tm_4.common.extension.toDate
import com.kuzmin.tm_4.core.network.model.preview.AddressSampleDto
import com.kuzmin.tm_4.core.network.model.preview.ConstructionSampleDto
import com.kuzmin.tm_4.core.network.model.preview.SiteSampleDto
import com.kuzmin.tm_4.core.network.model.site.TenantDto
import com.kuzmin.tm_4.feature.api.domain.model.site_related_model.site.Tenant
import com.kuzmin.tm_4.feature.api.domain.model.site_related_model.site.sample.AddressSample
import com.kuzmin.tm_4.feature.api.domain.model.site_related_model.site.sample.ConstructionSample
import com.kuzmin.tm_4.feature.api.domain.model.site_related_model.site.sample.SiteSample

class SitesSamplesDtoToModelMapper {

    private fun mapSiteSampleDtoToSiteSample(siteSampleDto: SiteSampleDto): SiteSample {
        with(siteSampleDto) {
            return SiteSample(
                uuid = uuid,
                name = name,
                type = type,
                typeDescription = typeDescription,
                description = description,
                photoUrl = photoUrl,
                photoDimension = photoDimension,
                tenant = mapTenantDtoToTenant(tenant),
                latitude = latitude,
                longitude = longitude,
                address = mapAddressDtoToAddressPreview(address),
                constructionsSample = constructionsSample?.let {
                    mapConstructionsSampleDtoToConstructionsSample(it)
                }
            )
        }
    }

    fun mapSitesSampleDtoToSitesSample(sitesSampleDto: List<SiteSampleDto>?): List<SiteSample>? {
        Log.d("Mapper", "sitesSampleDto: ${sitesSampleDto.toString()}")
        return sitesSampleDto?.let { sites ->
            sites.map { mapSiteSampleDtoToSiteSample(it) }
        }
    }

    private fun mapConstructionSampleDtoToConstructionSample(constructionSampleDto: ConstructionSampleDto): ConstructionSample {
        with(constructionSampleDto) {
            return ConstructionSample(
                uuid = uuid,
                constructionType = constructionType,
                config = config,
                heightMm = heightMm,
                creationDate = creationDate.toDate(),
                completedDate = completedDate?.toDate(),
                isCompleted = isCompleted
            )
        }
    }

    private fun mapConstructionsSampleDtoToConstructionsSample(constructionsSampleDto: List<ConstructionSampleDto>): List<ConstructionSample> {
        return constructionsSampleDto.map {
            mapConstructionSampleDtoToConstructionSample(it)
        }
    }

    private fun mapTenantDtoToTenant(tenantDto: TenantDto): Tenant {
        return Tenant(
            name = tenantDto.name,
            logo = tenantDto.logo
        )
    }

    private fun mapAddressDtoToAddressPreview(addressSampleDto: AddressSampleDto): AddressSample {
        with(addressSampleDto) {
            return AddressSample(
                country = country,
                city = city,
                street = street,
                building = building,
                region = region,
                subRegion = subRegion,
                postalCode = postalCode
            )
        }
    }

    /*private fun selectConsistentGroupsDto(groupsDto: List<GroupDto>, uuid: String): List<GroupDto> {
        return if (groupsDto.isNullOrEmpty()) mutableListOf()
        else groupsDto.filter { it.uuid == uuid }
    }

    private fun selectConsistentMeasurementsDto(measurements: List<MeasurementDto>, uuid: String): List<MeasurementDto> {
        return if (measurements.isNullOrEmpty()) mutableListOf()
        else measurements.filter { it.uuid == uuid }
    }

    private fun selectConsistentResultsDto(results: List<ResultDto>, uuid: String): List<ResultDto> {
        return if (results.isNullOrEmpty()) mutableListOf()
        else results.filter { it.uuid == uuid }
    }*/
}
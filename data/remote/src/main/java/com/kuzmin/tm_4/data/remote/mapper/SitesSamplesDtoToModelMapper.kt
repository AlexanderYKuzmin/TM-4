package com.kuzmin.tm_4.data.remote.mapper

import android.util.Log
import com.kuzmin.tm_4.common.extension.toDate
import com.kuzmin.tm_4.core.network.model.preview.AddressSampleDto
import com.kuzmin.tm_4.core.network.model.preview.ConstructionSampleDto
import com.kuzmin.tm_4.core.network.model.preview.SiteSampleDto
import com.kuzmin.tm_4.core.network.model.site.TenantDto
import com.kuzmin.tm_4.feature.sites.domain.model.samples.AddressSample
import com.kuzmin.tm_4.feature.sites.domain.model.samples.ConstructionSample
import com.kuzmin.tm_4.feature.sites.domain.model.samples.SiteSample
import com.kuzmin.tm_4.feature.sites.domain.model.Tenant

class SitesSamplesDtoToModelMapper {

    private fun mapSiteSampleDtoToSiteSample(siteSampleDto: SiteSampleDto): SiteSample {
        return SiteSample(
            remoteId = siteSampleDto.id,
            uuid = siteSampleDto.uuid,
            name = siteSampleDto.name,
            type = siteSampleDto.type,
            typeDescription = siteSampleDto.typeDescription,
            description = siteSampleDto.description,
            photoUrl = siteSampleDto.photoUrl,
            photoDimension = siteSampleDto.photoDimension,
            tenant = mapTenantDtoToTenant(siteSampleDto.tenant),
            latitude = siteSampleDto.latitude,
            longitude = siteSampleDto.longitude,
            address = mapAddressDtoToAddressPreview(siteSampleDto.address),
            constructionsSample = siteSampleDto.constructionsSample?.let {
                mapConstructionsSampleDtoToConstructionsSample(it)
            }
        )
    }

    fun mapSitesSampleDtoToSitesSample(sitesSampleDto: List<SiteSampleDto>?): List<SiteSample>? {
        Log.d("Mapper", "sitesSampleDto: ${sitesSampleDto.toString()}")
        return sitesSampleDto?.let { sites ->
            sites.map { mapSiteSampleDtoToSiteSample(it) }
        }
    }

    private fun mapConstructionSampleDtoToConstructionSample(constructionSampleDto: ConstructionSampleDto): ConstructionSample {
        return ConstructionSample(
            constructionSampleDto.constructionType,
            constructionSampleDto.config,
            constructionSampleDto.heightMm,
            constructionSampleDto.creationDate?.toDate(),
            constructionSampleDto.completedDate?.toDate(),
            constructionSampleDto.isCompleted
        )
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
        return AddressSample(
            country = addressSampleDto.country,
            city = addressSampleDto.city,
            street = addressSampleDto.street,
            building = addressSampleDto.building,
            region = addressSampleDto.region,
            subRegion = addressSampleDto.subRegion,
            postalCode = addressSampleDto.postalCode
        )
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
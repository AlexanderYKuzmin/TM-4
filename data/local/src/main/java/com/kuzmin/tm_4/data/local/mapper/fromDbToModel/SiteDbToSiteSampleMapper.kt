package com.kuzmin.tm_4.data.local.mapper.fromDbToModel

import com.kuzmin.tm_4.common.extension.toDate
import com.kuzmin.tm_4.core.database.model.delivery.SiteDb
import com.kuzmin.tm_4.core.database.model.site.AddressDb
import com.kuzmin.tm_4.core.database.model.site.ConstructionDb
import com.kuzmin.tm_4.core.database.model.site.MeasurementConstructionDb
import com.kuzmin.tm_4.core.database.model.site.MeasurementDb
import com.kuzmin.tm_4.feature.api.domain.model.site_related_model.site.sample.AddressSample
import com.kuzmin.tm_4.feature.api.domain.model.site_related_model.site.sample.ConstructionSample
import com.kuzmin.tm_4.feature.api.domain.model.site_related_model.site.sample.SiteSample
import javax.inject.Inject

class SiteDbToSiteSampleMapper @Inject constructor(

) : SiteDbToSiteModelMapper() {

    fun mapSiteDbListToSiteSampleList(siteDbList: List<SiteDb>): List<SiteSample> {
        if (siteDbList.isEmpty()) return emptyList()
        return siteDbList.map {
            mapSiteDbToSiteSample(it)
        }
    }

    fun mapSiteDbToSiteSample(siteDb: SiteDb): SiteSample {
        with(siteDb) {
            return SiteSample(
                uuid = siteParamsDb.uuid,
                name = siteParamsDb.name,
                type = siteParamsDb.siteType,
                typeDescription = siteParamsDb.siteTypeDescription,
                description = siteParamsDb.description,
                photoUrl = null,
                photoDimension = null,
                tenant = mapTenantDbToTenant(tenantDb),
                latitude = siteParamsDb.latitude,
                longitude = siteParamsDb.longitude,
                address = mapAddressDbToAddressSample(addressDb),
                constructionsSample =
                    mapConstructionDbListToConstructionSampleList(
                        constructions,
                        measurementsConstructions
                    )
            )
        }

    }

    private fun mapAddressDbToAddressSample(addressDb: AddressDb): AddressSample {
        with(addressDb) {
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

    private fun mapConstructionDbListToConstructionSampleList(
        constructionDbList: List<ConstructionDb>,
        mcDbList: List<MeasurementConstructionDb>
    ): List<ConstructionSample> {
        if (constructionDbList.isEmpty()) return emptyList()
        return constructionDbList.map { cDb ->
            mapConstructionDbToConstructionSample(
                cDb,
                if (mcDbList.isNotEmpty()) mcDbList.first { mcDb ->
                    mcDb.constructionUuid == cDb.uuid
                } else {
                    null
                }
            )
        }
    }

    private fun mapConstructionDbToConstructionSample(
        constructionDb: ConstructionDb,
        mcDb: MeasurementConstructionDb?
    ): ConstructionSample {
        with(constructionDb) {
            return ConstructionSample(
                uuid = uuid,
                constructionType = constructionType,
                config = config,
                heightMm = height,
                creationDate = cDate.toDate(),
                completedDate =  mcDb?.completedDate?.toDate(),
                isCompleted = mcDb?.isCompleted ?: false
            )
        }
    }
}
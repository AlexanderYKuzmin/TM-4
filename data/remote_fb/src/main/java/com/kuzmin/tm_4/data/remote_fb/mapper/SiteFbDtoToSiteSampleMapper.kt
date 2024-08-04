package com.kuzmin.tm_4.data.remote_fb.mapper

import android.util.Log
import com.kuzmin.tm_4.core.network_fb.model.ConstructionFbDto
import com.kuzmin.tm_4.core.network_fb.model.SiteFbDto
import com.kuzmin.tm_4.feature.api.domain.model.Tenant
import com.kuzmin.tm_4.feature.api.domain.model.sample.AddressSample
import com.kuzmin.tm_4.feature.api.domain.model.sample.ConstructionSample
import com.kuzmin.tm_4.feature.api.domain.model.sample.SiteSample
import java.util.Date
import javax.inject.Inject

class SiteFbDtoToSiteSampleMapper @Inject constructor() {
    fun mapSiteFbDtoMapToSiteSampleModelList(
        siteFbDtoMap: Map<SiteFbDto?, List<ConstructionFbDto?>>
    ): List<SiteSample> {
        Log.d("getAll", "Mapper siteFB to sites. siteFbDtoMap = $siteFbDtoMap")
        siteFbDtoMap.filterKeys { it != null }
        return siteFbDtoMap.entries.map { mapSiteFbDtoToSiteSample(it.key!!, it.value.filterNotNull()) }
    }

    fun mapSiteFbDtoToSiteSample(siteFbDto: SiteFbDto, constructionsFbDto: List<ConstructionFbDto>): SiteSample {
        with(siteFbDto) {
            return SiteSample(
                uuid = uuid,
                name = name,
                type = type.toInt(),
                typeDescription = typeDescription,
                description = description,
                photoUrl = null,
                photoDimension = null,
                tenant = Tenant(name = tenantName, logo = null),
                latitude = geo.latitude,
                longitude = geo.longitude,
                address = AddressSample(
                    country = address.country,
                    city = address.city,
                    street = address.street,
                    building = address.building,
                    region = address.region,
                    subRegion = address.subRegion,
                    postalCode = address.postalCode
                ),
                constructionsSample =
                    mapConstructionFbDtoListToConstructionSampleList(constructionsFbDto)
            )
        }
    }

    private fun mapConstructionFbDtoListToConstructionSampleList(
        constructionFbDtoList: List<ConstructionFbDto>
    ): List<ConstructionSample> {
        return constructionFbDtoList.map { mapConstructionFbDtoToConstructionSample(it) }
    }

    private fun mapConstructionFbDtoToConstructionSample(constructionFbDto: ConstructionFbDto): ConstructionSample {
        with(constructionFbDto) {
            return ConstructionSample(
                uuid = uuid,
                constructionType = type,
                config = config,
                heightMm = height,
                creationDate = if (cDate > 0) Date(cDate) else null,
                completedDate = if (mDate > 0) Date(mDate) else null,
                isCompleted = isMeasured
            )
        }
    }
}
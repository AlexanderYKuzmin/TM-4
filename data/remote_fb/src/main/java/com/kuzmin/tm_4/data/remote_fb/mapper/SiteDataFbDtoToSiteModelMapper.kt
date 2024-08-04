package com.kuzmin.tm_4.data.remote_fb.mapper

import android.util.Log
import com.kuzmin.tm_4.core.network_fb.model.AddressFbDto
import com.kuzmin.tm_4.core.network_fb.model.ConstructionFbDto
import com.kuzmin.tm_4.core.network_fb.model.GroupFbDto
import com.kuzmin.tm_4.core.network_fb.model.MeasurementConstructionFbDto
import com.kuzmin.tm_4.core.network_fb.model.MeasurementFbDto
import com.kuzmin.tm_4.core.network_fb.model.ResultFbDto
import com.kuzmin.tm_4.core.network_fb.model.SectionFbDto
import com.kuzmin.tm_4.core.network_fb.model.SiteFbDto
import com.kuzmin.tm_4.data.remote_fb.model_fb.SiteDataFbDto
import com.kuzmin.tm_4.feature.api.domain.model.site.McLevelInfo
import com.kuzmin.tm_4.feature.api.domain.model.Tenant
import com.kuzmin.tm_4.feature.api.domain.model.site.Address
import com.kuzmin.tm_4.feature.api.domain.model.site.Construction
import com.kuzmin.tm_4.feature.api.domain.model.site.Group
import com.kuzmin.tm_4.feature.api.domain.model.site.Measurement
import com.kuzmin.tm_4.feature.api.domain.model.site.MeasurementConstruction
import com.kuzmin.tm_4.feature.api.domain.model.site.Result
import com.kuzmin.tm_4.feature.api.domain.model.site.Section
import com.kuzmin.tm_4.feature.api.domain.model.site.Site
import com.kuzmin.tm_4.feature.api.domain.model.site.SiteParams
import java.util.Date
import java.util.UUID
import javax.inject.Inject

class SiteDataFbDtoToSiteModelMapper @Inject constructor(

) {
    fun mapSiteDataFbDtoToSite(
        siteDataFbDto: SiteDataFbDto
    ): Site {
        with(siteDataFbDto) {
            Log.d("fb", "Mapper Site. Sections: $sections")
            Log.d("fb", "Mapper Site. MeasurementConstructions: $measurementConstructions")
            return Site(
                siteParams = mapSiteParamsDtoToSiteParams(siteFbDto),
                tenant = mapTenantDtoToTenant(siteFbDto),
                address = mapAddressFbDtoToAddress(siteFbDto.address),
                photos =  emptyList(),
                siteEquipments =  emptyList(),
                constructions =  mapConstructionsDtoListToConstructionList(constructions),
                measurementsConstructions =  mapMeasurementConstructionFbDtoListToMeasurementConstruction(
                    measurementConstructions
                ),
                //constructionsLevels = mapLevelsDataDtoToLevelsInfo(),
                constructionsSections = mapSectionsDtoToSectionList(sections),
                measurementsGroups = mapGroupsDtoToGroupList(groups),
                measurements = mapMeasurementsDtoToMeasurementList(measurements),
                results = mapResultsDtoToResultList(results),
                levelsInfo = mapLevelsInfoFbDtoToLevelsInfo(
                    measurementConstructions,
                    sections,
                    siteFbDto.uuid
                )
            )
        }
    }

    private fun mapSiteParamsDtoToSiteParams(siteFbDto: SiteFbDto): SiteParams {
        return SiteParams(
            siteUuid = siteFbDto.uuid,
            name = siteFbDto.name,
            description = siteFbDto.description,
            latitude = siteFbDto.geo.latitude,
            longitude = siteFbDto.geo.longitude,
            siteType = siteFbDto.type.toInt(),
            siteTypeDescription = siteFbDto.typeDescription
        )
    }

    private fun mapTenantDtoToTenant(siteFbDto: SiteFbDto): Tenant {
        return Tenant(
            uuid = siteFbDto.uuid,
            name = siteFbDto.tenantName,
            logo = null
        )
    }

    private fun mapAddressFbDtoToAddress(addressFbDto: AddressFbDto): Address {
        with(addressFbDto) {
            return Address(
                uuid = uuid,
                country = country,
                region = region,
                regionCode = regionCode.toInt(),
                subRegion = subRegion,
                city = city,
                street = street,
                building = building,
                postalCode = postalCode
            )
        }
    }

    private fun mapConstructionsDtoListToConstructionList(
        constructionsFbDto: Map<String, List<ConstructionFbDto>>,
    ): List<Construction> {
        return constructionsFbDto.values.flatten().map { mapConstructionDtoToConstruction(it) }
    }

    private fun mapConstructionDtoToConstruction(
        constructionFbDto: ConstructionFbDto,
    ): Construction {
        with(constructionFbDto) {
            return Construction(
                uuid = uuid,
                version = version,
                description = description,
                status = status,
                numOfSections = qSections,
                height = height,
                constructionType = type,
                config = config,
                measureLevels = qLevels,
                siteUuid = siteUuid,
                cDate = Date(cDate)
            )
        }
    }

    fun mapMeasurementConstructionFbDtoListToMeasurementConstruction(
        measurementConstructionsFbDto: Map<String, List<MeasurementConstructionFbDto>>?,
    ): List<MeasurementConstruction> {
        if (measurementConstructionsFbDto.isNullOrEmpty()) return emptyList()
        return measurementConstructionsFbDto.values
            .flatten()
            .map { mapMeasurementConstructionFbDtoToMeasurementConstruction(it) }
    }

    private fun mapMeasurementConstructionFbDtoToMeasurementConstruction(
        measurementConstructionFbDto: MeasurementConstructionFbDto,
    ): MeasurementConstruction {
        with(measurementConstructionFbDto) {
            return MeasurementConstruction(
                uuid = uuid,
                measurementName = measurementName,
                creatorUuid = creatorUuid,
                startLevel = startLevel,
                creationDate = Date(cDate),
                completedDate = Date(completedDate),
                isCompleted = isCompleted,
                employeeUuid = employeeUuid,
                constructionUuid = constructionUuid,
                employeeName = employeeName,
                creatorName = creatorName,
                isServiceable = conclusion
            )
        }
    }

    private fun mapLevelsInfoFbDtoToLevelsInfo(
        mcFbDtoMap: Map<String, List<MeasurementConstructionFbDto>>?,
        sectionsFbDtoMap: Map<String, List<SectionFbDto>>?,
        sUuid: String
    ) : List<McLevelInfo>? {
        Log.d("mapper", "Sections map: $sectionsFbDtoMap")
        if (mcFbDtoMap.isNullOrEmpty()) return null

        if (sectionsFbDtoMap.isNullOrEmpty()) return null

        val levelInfo = mutableListOf<McLevelInfo>()
        for (k in mcFbDtoMap.keys) {  // for each construction
            val sections = sectionsFbDtoMap[k]!!
            Log.d("mapper", "sections in cycle. ${sections.size}")

            if (mcFbDtoMap[k].isNullOrEmpty()) continue

            mcFbDtoMap[k]!!.forEach {

                val altitudes = createAltitudeList(sections, it.startLevel)
                Log.d("mapper", "altitudes size. ${altitudes.size}")
                for (i in it.resultByLevels.indices) {
                    levelInfo.add(
                        McLevelInfo(
                            uuid = UUID.randomUUID().toString(),
                            levelNum = i,
                            shift = it.resultByLevels[i],
                            isServiceable = it.failedLevels[i],
                            altitude = altitudes[i],
                            mcUuid = it.uuid,
                            sUuid = sUuid
                        )
                    )
                }
            }
        }
        Log.d("mapper", "LevelInfo:")
        levelInfo.forEach { Log.d("mapper", "$it") }
        return levelInfo
    }

    private fun createAltitudeList(sectionsFbDto: List<SectionFbDto>, startLevel: Int): List<Int> {
        val altList = mutableListOf<Int>()
        var altValue = startLevel
        altList.add(altValue)
        sectionsFbDto.sortedBy { it.number }.forEach {
            altValue += it.height
            altList.add(altValue)
        }
        return altList
    }

    private fun mapSectionsDtoToSectionList(sections: Map<String, List<SectionFbDto>>?): List<Section> {
        if (sections.isNullOrEmpty()) return emptyList()
        return sections.values
            .flatten()
            .map { mapSectionFbDtoToSection(it) }
    }

    private fun mapSectionFbDtoToSection(sectionFbDto: SectionFbDto): Section {
        with(sectionFbDto) {
            return Section(
                uuid = uuid,
                number = number,
                wBottom = wBottom,
                wTop = wTop,
                height = height,
                level = null,
                status = status,
                constructionUuid = constructionUuid
            )
        }
    }

    private fun mapGroupsDtoToGroupList(groups: Map<String, List<GroupFbDto>>?): List<Group> {
        if (groups.isNullOrEmpty()) return emptyList()
        return groups.values
            .flatten()
            .map { mapGroupFbDtoToGroup(it) }
    }

    private fun mapGroupFbDtoToGroup(groupFbDto: GroupFbDto): Group {
        with(groupFbDto) {
            return Group(
                uuid = uuid,
                groupNum = groupNum,
                azimuth = azimuth,
                theoDistance = theoDistance,
                theoHeight = theoHeight,
                measurementConstructionUuid = mcUuid
            )
        }
    }

    private fun mapMeasurementsDtoToMeasurementList(measurements: Map<String, List<MeasurementFbDto>>?): List<Measurement> {
        if (measurements.isNullOrEmpty()) return emptyList()
        return measurements.values
            .flatten()
            .map { mapMeasurementFbDtoToMeasurement(it) }
    }

    private fun mapMeasurementFbDtoToMeasurement(measurementFbDto: MeasurementFbDto): Measurement {
        with(measurementFbDto) {
            return Measurement(
                uuid = uuid,
                level = level,
                leftAngleCl = leftAngleCl,
                leftAngleCr = leftAngleCr,
                rightAngleCr = rightAngleCr,
                rightAngleCl = rightAngleCl,
                measurementGroupUuid = groupUuid
            )
        }
    }

    private fun mapResultsDtoToResultList(results: Map<String, List<ResultFbDto>>?): List<Result> {
        if (results.isNullOrEmpty()) return emptyList()
        return results.values
            .flatten()
            .map { mapResultFbDtoToResult(it) }
    }

    private fun mapResultFbDtoToResult(resultFbDto: ResultFbDto): Result {
        with(resultFbDto) {
            return Result(
                uuid = uuid,
                level = level,
                sectionUuid = "",
                averageCl = averageCl,
                averageCr = averageCr,
                averageClCr = averageClCr,
                shiftDeg = shiftDeg,
                shiftMm = shiftMm,
                tanAlpha = tanAlfa,
                distToMeasureLevel = distToMeasureLevel,
                distDelta = distDelta,
                betaAverageLeft = betaAverageLeft,
                betaAverageRight = betaAverageRight,
                betI = betaI,
                betaDelta = betaDelta,
                measurementUuid = measurementUuid,
                measurementGroupUuid = groupUuid
            )
        }
    }
}
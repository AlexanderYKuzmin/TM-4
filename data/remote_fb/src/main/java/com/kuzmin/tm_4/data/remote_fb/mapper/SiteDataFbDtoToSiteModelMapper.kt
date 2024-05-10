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
import com.kuzmin.tm_4.feature.api.model.McLevelInfo
import com.kuzmin.tm_4.feature.api.model.Tenant
import com.kuzmin.tm_4.feature.api.model.site.Address
import com.kuzmin.tm_4.feature.api.model.site.Construction
import com.kuzmin.tm_4.feature.api.model.site.Group
import com.kuzmin.tm_4.feature.api.model.site.Measurement
import com.kuzmin.tm_4.feature.api.model.site.MeasurementConstruction
import com.kuzmin.tm_4.feature.api.model.site.Result
import com.kuzmin.tm_4.feature.api.model.site.Section
import com.kuzmin.tm_4.feature.api.model.site.Site
import com.kuzmin.tm_4.feature.api.model.site.SiteParams
import java.util.Date
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
                measurementsConstructions =  mapMeasurementConstructionFbDtoListToMeasurementConstruction(measurementConstructions),
                //constructionsLevels = mapLevelsDataDtoToLevelsInfo(),
                constructionsSections = mapSectionsDtoToSectionList(sections),
                measurementsGroups = mapGroupsDtoToGroupList(groups),
                measurements = mapMeasurementsDtoToMeasurementList(measurements),
                results = mapResultsDtoToResultList(results)
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
                siteUuid = siteUuid
            )
        }
    }

    fun mapMeasurementConstructionFbDtoListToMeasurementConstruction(
        measurementConstructionsFbDto: Map<String, List<MeasurementConstructionFbDto>>?
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
                isServiceable = conclusion,
                levelsInfo = mapLevelsDataDtoToLevelsInfo(resultByLevels, failedLevels)
            )
        }
    }

    private fun mapLevelsDataDtoToLevelsInfo(
        resultByLevels: List<Int>,
        failedLevels: List<Boolean>
    ): List<McLevelInfo> {
        val levelInfo = mutableListOf<McLevelInfo>()

        for (i in resultByLevels.indices) {
            Log.d("getAll", "Map levels. index i = $i")
            Log.d("getAll", "Level shift. shift = ${resultByLevels[i]}")
            Log.d("getAll", "Level failed. isGood = ${failedLevels[i]}")

            levelInfo.add(
                McLevelInfo(
                    levelNum = i,
                    shift = resultByLevels[i],
                    isServiceable = failedLevels[i]
                )
            )
        }
        return levelInfo
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
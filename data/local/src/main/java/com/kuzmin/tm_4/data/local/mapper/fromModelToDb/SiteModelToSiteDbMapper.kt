package com.kuzmin.tm_4.data.local.mapper.fromModelToDb

import android.util.Log
import com.kuzmin.tm_4.common.extension.formatToDateSqlString
import com.kuzmin.tm_4.core.database.model.site.AddressDb
import com.kuzmin.tm_4.core.database.model.site.ConstructionDb
import com.kuzmin.tm_4.core.database.model.site.GroupDb
import com.kuzmin.tm_4.core.database.model.site.MeasurementConstructionDb
import com.kuzmin.tm_4.core.database.model.site.MeasurementDb
import com.kuzmin.tm_4.core.database.model.site.PhotoDb
import com.kuzmin.tm_4.core.database.model.site.ResultDb
import com.kuzmin.tm_4.core.database.model.site.SectionDb
import com.kuzmin.tm_4.core.database.model.delivery.SiteDb
import com.kuzmin.tm_4.core.database.model.site.LevelDb
import com.kuzmin.tm_4.core.database.model.site.SiteEquipmentDb
import com.kuzmin.tm_4.core.database.model.site.SiteParamsDb
import com.kuzmin.tm_4.core.database.model.site.TenantDb
import com.kuzmin.tm_4.feature.api.domain.model.site.McLevelInfo
import com.kuzmin.tm_4.feature.api.domain.model.Tenant
import com.kuzmin.tm_4.feature.api.domain.model.site.Address
import com.kuzmin.tm_4.feature.api.domain.model.site.Construction
import com.kuzmin.tm_4.feature.api.domain.model.site.Group
import com.kuzmin.tm_4.feature.api.domain.model.site.Measurement
import com.kuzmin.tm_4.feature.api.domain.model.site.MeasurementConstruction
import com.kuzmin.tm_4.feature.api.domain.model.site.Photo
import com.kuzmin.tm_4.feature.api.domain.model.site.Section
import com.kuzmin.tm_4.feature.api.domain.model.site.Site
import com.kuzmin.tm_4.feature.api.domain.model.site.SiteEquipment
import com.kuzmin.tm_4.feature.api.domain.model.site.SiteParams
import com.kuzmin.tm_4.feature.api.domain.model.site.Result
import java.util.Date

import javax.inject.Inject

open class SiteModelToSiteDbMapper @Inject constructor(

) {

    /*fun mapSitesModelToSitesDb(sites: List<Site>): List<SiteDb> {
        if (sites.isEmpty()) return emptyList()
        return sites.map {
            mapSiteModelToSiteDb(it)
        }
    }*/

    fun mapSiteModelToSiteDb(site: Site, durability: String): SiteDb {

        with(site) {
           return SiteDb(
               siteParamsDb = mapSiteParamsToSiteParamsDb(siteParams, durability),
               tenantDb =  mapTenantToTenantDb(tenant, siteParams.siteUuid),
               addressDb = mapAddressToAddressDb(address, siteParams.siteUuid) ,
               siteEquipments = mapSiteEquipmentsToSiteEquipmentsDb(siteEquipments, siteParams.siteUuid),
               photos =  mapPhotosToPhotosDb(photos, siteParams.siteUuid),
               constructions =  mapConstructionsToConstructionsDb(constructions),
               sections = mapSectionsToSectionsDb(constructionsSections, siteParams.siteUuid),
               groups = mapGroupsToGroupsDb(measurementsGroups, siteParams.siteUuid),
               measurementsConstructions = mapMcsToMcsDb(measurementsConstructions, siteParams.siteUuid),
               measurements = mapMeasuresToMeasuresDb(measurements, siteParams.siteUuid, measurementsGroups),
               results = mapResultsToResultsDb(results, siteParams.siteUuid, measurementsGroups),
               levelsInfo = mapLevelsInfoToLevelsDb(levelsInfo)
           )
        }
    }

    private fun mapSiteParamsToSiteParamsDb(siteParams: SiteParams, durability: String): SiteParamsDb {
        with(siteParams) {
            return SiteParamsDb(
                uuid = siteUuid,
                siteUuid = siteUuid,
                name = name,
                description = description,
                latitude = latitude,
                longitude = longitude,
                siteType = siteType,
                siteTypeDescription = siteTypeDescription,
                durability = durability
            )
        }
    }

    private fun mapTenantToTenantDb(tenant: Tenant, siteUuid: String): TenantDb {
        with(tenant) {
            return TenantDb(
                uuid = uuid,
                name = name,
                logo = logo,
                siteUuid = siteUuid
            )
        }
    }

    private fun mapAddressToAddressDb(address: Address, siteUuid: String): AddressDb {
        with(address) {
            return AddressDb(
                uuid = uuid,
                siteUuid = siteUuid,
                country = country,
                region = region,
                regionCode = regionCode,
                subRegion = subRegion,
                city = city,
                street = street,
                building = building,
                postalCode = postalCode
            )
        }
    }

    private fun mapSiteEquipmentsToSiteEquipmentsDb(siteEquipments: List<SiteEquipment>, siteUuid: String): List<SiteEquipmentDb> {
        if (siteEquipments.isEmpty()) return emptyList()
        return siteEquipments.map {
            mapSiteEquipmentToSiteEquipmentDb(it, siteUuid)
        }
    }

    private fun mapSiteEquipmentToSiteEquipmentDb(siteEquipment: SiteEquipment, siteUuid: String): SiteEquipmentDb {
        with(siteEquipment) {
            return SiteEquipmentDb(
                uuid = uuid,
                siteUuid = siteUuid,
                type = type,
                name = name
            )
        }
    }

    private fun mapPhotosToPhotosDb(photos: List<Photo>, siteUuid: String): List<PhotoDb> {
        if (photos.isEmpty()) return emptyList()
        return photos.map {
            mapPhotoToPhotoDb(it, siteUuid)
        }
    }

    private fun mapPhotoToPhotoDb(photo: Photo, siteUuid: String): PhotoDb {
        with(photo) {
            return PhotoDb(
               uuid = uuid ?: "",
               siteUuid = siteUuid,
               name =  name ?: "",
               date = date?.let { it.formatToDateSqlString() } ?: Date().formatToDateSqlString(),
               url = url,
               urlThumbnail = urlThumbnail ?: "",
               employeeId = employeeUuid ?: -1,
               employeeName = employeeName ?: "",
               dimensions = String.format("%dx%d", dimensionXPx, dimensionYPx),
               thumbnailDim = String.format("%dx%d", thumbnailDimXPx, thumbnailDimYPx)
            )
        }
    }

    fun mapConstructionsToConstructionsDb(constructions: List<Construction>): List<ConstructionDb> {
        if (constructions.isEmpty()) return emptyList()
        return constructions.map {
            mapConstructionToConstructionDb(it)
        }
    }

    fun mapConstructionToConstructionDb(construction: Construction): ConstructionDb {
        with(construction) {
            return ConstructionDb(
                uuid = uuid,
                version = version,
                description = description,
                status = status,
                numOfSections = numOfSections,
                height = height,
                constructionType = constructionType,
                config = config,
                measureLevels = measureLevels,
                siteUuid = siteUuid
            )
        }
    }

    fun mapSectionsToSectionsDb(sections: List<Section>?, siteUuid: String): List<SectionDb> {
        if (sections.isNullOrEmpty()) return emptyList()
        return sections.map {
            mapSectionToSectionDb(it, siteUuid)
        }
    }

    private fun mapSectionToSectionDb(section: Section, siteUuid: String): SectionDb {
        with(section) {
            return SectionDb(
                uuid = uuid,
                siteUuid = siteUuid,
                constructionUuid = constructionUuid,
                number = number,
                wBottom = wBottom,
                wTop = wTop,
                height = height,
                level = level,
                status = status
            )
        }
    }

    private fun mapGroupsToGroupsDb(groups: List<Group>?, siteUuid: String): List<GroupDb> {
        if (groups.isNullOrEmpty()) return emptyList()
        return groups.map {
            mapGroupToGroupDb(it, siteUuid)
        }
    }

    private fun mapGroupToGroupDb(group: Group, siteUuid: String): GroupDb {
        with(group) {
            return GroupDb(
                uuid = uuid,
                siteUuid = siteUuid,
                measurementConstructionUuid = measurementConstructionUuid,
                groupNum = groupNum,
                azimuth = azimuth,
                theoDistance = theoDistance,
                theoHeight = theoHeight
            )
        }
    }

    private fun mapMcsToMcsDb(measurementConstructions: List<MeasurementConstruction>?, siteUuid: String): List<MeasurementConstructionDb> {
        if (measurementConstructions.isNullOrEmpty()) return emptyList()
        return measurementConstructions.map {
            mapMcToMcDb(it, siteUuid)
        }
    }

    private fun mapMcToMcDb(measurementConstruction: MeasurementConstruction, siteUuid: String): MeasurementConstructionDb {
        Log.d("db", "Mapper. Map MC to MCDb. mc: $measurementConstruction, site uuid: $siteUuid")
        with(measurementConstruction) {
            return MeasurementConstructionDb(
                uuid = uuid,
                siteUuid = siteUuid,
                constructionUuid = constructionUuid,
                measurementName = measurementName,
                creatorUuid = creatorUuid,
                startLevel = startLevel,
                creationDate = creationDate.formatToDateSqlString(),
                completedDate = completedDate?.formatToDateSqlString() ?: "",
                isCompleted = isCompleted,
                employeeUuid = employeeUuid,
                employeeName = employeeName,
                creatorName = creatorName,
                isServiceable = isServiceable
            )
        }
    }

    private fun mapMeasuresToMeasuresDb(
        measurements: List<Measurement>?,
        siteUuid: String,
        groups: List<Group>?
    ): List<MeasurementDb> {
        if (measurements.isNullOrEmpty() || groups.isNullOrEmpty()) return emptyList()
        return measurements.map { measure ->
            val measurementConstructionUuid = groups.first { group ->
                group.uuid == measure.measurementGroupUuid
            }.measurementConstructionUuid
            mapMeasureToMeasureDb(measure, siteUuid, measurementConstructionUuid)
        }
    }

    private fun mapMeasureToMeasureDb(
        measurement: Measurement,
        siteUuid: String,
        measurementConstructionUuid: String
    ): MeasurementDb {
        with(measurement) {
            return MeasurementDb(
                uuid = uuid,
                siteUuid = siteUuid,
                measurementConstructionUuid = measurementConstructionUuid,
                measurementGroupUuid = measurementGroupUuid,
                level = level,
                leftAngleCl = leftAngleCl,
                leftAngleCr = leftAngleCr,
                rightAngleCr = rightAngleCl,
                rightAngleCl = rightAngleCr
            )
        }
    }

    private fun mapResultsToResultsDb(
        results: List<Result>?,
        siteUuid: String,
        groups: List<Group>?
    ): List<ResultDb> {
        if (results.isNullOrEmpty() || groups.isNullOrEmpty()) return emptyList()
        return results.map { result ->
            val measurementConstructionUuid = groups.first { group ->
                group.uuid == result.measurementGroupUuid
            }.measurementConstructionUuid
            mapResultToResultDb(result, siteUuid, measurementConstructionUuid)
        }
    }

    private fun mapResultToResultDb(
        result: Result,
        siteUuid: String,
        measurementConstructionUuid: String
    ): ResultDb {
        with(result) {
            return ResultDb(
                uuid = uuid,
                siteUuid = siteUuid,
                measurementConstructionUuid = measurementConstructionUuid,
                measurementGroupUuid = measurementGroupUuid,
                measurementUuid = measurementUuid,
                level = level,
                sectionUuid = siteUuid,
                averageCl = averageCl,
                averageCr = averageCr,
                averageClCr = averageClCr,
                shiftDeg = shiftDeg,
                shiftMm = shiftMm,
                tanAlpha = tanAlpha,
                distToMeasureLevel = distToMeasureLevel,
                distDelta = distDelta,
                betaAverageLeft = betaAverageLeft,
                betaAverageRight = betaAverageRight,
                betI = betI,
                betaDelta = betaDelta
            )
        }
    }

    private fun mapLevelsInfoToLevelsDb(levels: List<McLevelInfo>?): List<LevelDb> {
        if (levels.isNullOrEmpty()) return emptyList()

        return levels.map { mapLevelToLevelDb(it) }
    }

    private fun mapLevelToLevelDb(level: McLevelInfo): LevelDb {
        with(level) {
            return LevelDb(
                uuid = uuid,
                levelNum = levelNum,
                shift = shift,
                isServiceable = isServiceable,
                altitude = altitude,
                mcUuid = mcUuid,
                sUuid = sUuid
            )
        }
    }
}
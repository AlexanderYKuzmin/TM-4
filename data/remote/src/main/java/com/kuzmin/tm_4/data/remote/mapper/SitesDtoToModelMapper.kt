package com.kuzmin.tm_4.data.remote.mapper

import android.util.Log
import com.kuzmin.tm_4.common.extension.getX
import com.kuzmin.tm_4.common.extension.getY
import com.kuzmin.tm_4.common.extension.toDate
import com.kuzmin.tm_4.common.extension.toMmInt
import com.kuzmin.tm_4.core.network.model.site.AddressDto
import com.kuzmin.tm_4.core.network.model.site.ConstructionDto
import com.kuzmin.tm_4.core.network.model.site.GroupDto
import com.kuzmin.tm_4.core.network.model.site.LevelDto
import com.kuzmin.tm_4.core.network.model.site.MeasurementConstructionDto
import com.kuzmin.tm_4.core.network.model.site.MeasurementDto
import com.kuzmin.tm_4.core.network.model.site.PhotoDto
import com.kuzmin.tm_4.core.network.model.site.ResultDto
import com.kuzmin.tm_4.core.network.model.site.SectionDto
import com.kuzmin.tm_4.core.network.model.site.SiteDto
import com.kuzmin.tm_4.core.network.model.site.SiteEquipmentDto
import com.kuzmin.tm_4.core.network.model.site.SiteParamsDto
import com.kuzmin.tm_4.core.network.model.site.TenantDto
import com.kuzmin.tm_4.feature.api.model.Tenant
import com.kuzmin.tm_4.feature.api.model.site.Address
import com.kuzmin.tm_4.feature.api.model.site.Construction
import com.kuzmin.tm_4.feature.api.model.site.Group
import com.kuzmin.tm_4.feature.api.model.site.Level
import com.kuzmin.tm_4.feature.api.model.site.Measurement
import com.kuzmin.tm_4.feature.api.model.site.MeasurementConstruction
import com.kuzmin.tm_4.feature.api.model.site.Photo
import com.kuzmin.tm_4.feature.api.model.site.Section
import com.kuzmin.tm_4.feature.api.model.site.Site
import com.kuzmin.tm_4.feature.api.model.site.SiteEquipment
import com.kuzmin.tm_4.feature.api.model.site.SiteParams
import com.kuzmin.tm_4.feature.api.model.site.Result

import java.util.Date

class SitesDtoToModelMapper {

    fun mapSitesDtoToSites(sitesDto: List<SiteDto>?): List<Site>? {
        return sitesDto?.map {
            mapSiteDtoToSite(it)
        }
    }

    fun mapSiteDtoToSite(siteDto: SiteDto): Site {
        return Site(
            siteParams = mapSiteParamsDtoToSiteParams(siteDto.siteParamsDto),
            tenant = mapTenantDtoToTenant(siteDto.tenantDto),
            address = mapAddressDtoToAddress(siteDto.addressDto),
            photos = mapPhotosDtoToPhotos(siteDto.photosDto),
            siteEquipments = mapSiteEquipmentsDtoToSiteEquipments(siteDto.siteEquipmentsDto),
            constructions = mapConstructionsDtoToConstructions(siteDto.constructionsDto),
            constructionsLevels = mapConstructionsLevelsDtoToConstructionsLevels(siteDto.constructionsLevelsDto),
            constructionsSections = mapConstructionsSectionsDtoToConstructionsSections(siteDto.constructionsSectionsDto),
            measurementsConstructions = mapMeasurementConstructionsDtoToMeasurementConstructions(
                siteDto.measurementsConstructionsDto
            ),
            measurementsGroups = mapMeasurementGroupsDtoToMeasurementGroups(siteDto.measurementsGroupsDto),
            measurements = mapMeasurementsDtoToMeasurements(siteDto.measurementsDto),
            results = mapResultsDtoToResults(siteDto.resultsDto)
        )
    }

    private fun mapSiteParamsDtoToSiteParams(siteParamsDto: SiteParamsDto): SiteParams {
        with(siteParamsDto) {
            return SiteParams(
                //remoteId = siteParamsDto.id,
                name = name,
                siteUuid = siteUuid,
                description = description,
                latitude = latitude,
                longitude = longitude,
                siteType = siteType,
                siteTypeDescription = siteTypeDescription
            )
        }
    }
    private fun mapConstructionsDtoToConstructions(constructionsDto: List<ConstructionDto>): List<Construction> {
        return if (constructionsDto.isEmpty()) {
            emptyList()
        } else constructionsDto.map {
            mapConstructionDtoToConstruction(it)
        }
    }
    private fun mapConstructionDtoToConstruction(constructionDto: ConstructionDto): Construction {
        with(constructionDto) {
            return Construction(
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

    private fun mapConstructionsLevelsDtoToConstructionsLevels(constructionsLevelsDto: List<LevelDto>?): List<Level> {
        return if (constructionsLevelsDto.isNullOrEmpty()) {
            emptyList()
        } else {
            constructionsLevelsDto.map { mapConstructionLevelDtoToConstructionLevel(it) }
        }
    }

    private fun mapConstructionLevelDtoToConstructionLevel(constructionLevelDto: LevelDto): Level {
        with(constructionLevelDto) {
            return Level(
                number = number,
                position = position,
                altitude = altitude
            )
        }
    }

    private fun mapConstructionsSectionsDtoToConstructionsSections(constructionsSectionsDto: List<SectionDto>): List<Section> {
        return if (constructionsSectionsDto.isEmpty()) {
            emptyList()
        } else {
            constructionsSectionsDto.map { mapConstructionSectionDtoToConstructionSection(it) }
        }
    }

    private fun mapConstructionSectionDtoToConstructionSection(constructionSectionDto: SectionDto): Section {
        with(constructionSectionDto) {
            return Section(
                uuid = uuid,
                number = number,
                wBottom = wBottom,
                wTop = wTop,
                height = height,
                level = level,
                status = status,
                constructionUuid = constructionUuid
            )
        }
    }

    private fun mapTenantDtoToTenant(tenantDto: TenantDto): Tenant {
        with(tenantDto) {
            return Tenant(
                uuid = uuid,
                name = name,
                logo = logo
            )
        }
    }
    private fun mapAddressDtoToAddress(addressDto: AddressDto): Address {
        with(addressDto) {
            return Address(
                uuid = uuid,
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

    private fun mapMeasurementConstructionsDtoToMeasurementConstructions(
        measurementConstructionsDto: List<MeasurementConstructionDto>
    ): List<MeasurementConstruction> {
        return if (measurementConstructionsDto.isEmpty()) {
            emptyList()
        } else {
            Log.d("Site", "MC dto length ${measurementConstructionsDto.size}")
            measurementConstructionsDto.map {
                mapMeasurementConstructionDtoToMeasurementConstruction(it)
            }
        }
    }

    private fun mapMeasurementConstructionDtoToMeasurementConstruction(
        measurementConstructionDto: MeasurementConstructionDto
    ): MeasurementConstruction {
        with(measurementConstructionDto) {
            return MeasurementConstruction(
                uuid = uuid,
                measurementName = measurementName,
                creatorUuid = creatorUuid,
                startLevel = startLevel,
                creationDate = creationDate.toDate() ?: Date(),
                completedDate = completedDate.toDate() ?: Date(),
                isCompleted = isCompleted,
                employeeUuid = employeeUuid,
                constructionUuid = constructionUuid,
                employeeName = employeeName,
                creatorName = creatorName,
                isServiceable = false // CHeck it
            )
        }
    }

    private fun mapMeasurementGroupsDtoToMeasurementGroups(groupsDto: List<GroupDto>): List<Group> {
        return if (groupsDto.isEmpty()) {
            emptyList()
        } else {
            groupsDto.map { mapMeasurementGroupDtoToMeasurementGroup(it) }
        }
    }

    private fun mapMeasurementGroupDtoToMeasurementGroup(groupDto: GroupDto): Group {
        with(groupDto) {
            return Group(
                uuid = uuid,
                measurementConstructionUuid = measurementConstructionUuid,
                groupNum = groupNum,
                azimuth = azimuth,
                theoDistance = theoDistance,
                theoHeight = theoHeight,
            )
        }
    }


    private fun mapMeasurementsDtoToMeasurements(measurementsDto: List<MeasurementDto>): List<Measurement> {
        return measurementsDto.map {
            mapMeasurementDtoToMeasurement(it)
        }
    }

    private fun mapMeasurementDtoToMeasurement(measurementDto: MeasurementDto): Measurement {
        return Measurement(
            uuid = measurementDto.uuid,
            measurementGroupUuid = measurementDto.measurementGroupUuid,
            level = measurementDto.level,
            leftAngleCl = measurementDto.leftAngleCl,
            leftAngleCr = measurementDto.leftAngleCr,
            rightAngleCl = measurementDto.rightAngleCl,
            rightAngleCr = measurementDto.rightAngleCr
        )
    }

    private fun mapResultsDtoToResults(resultsDto: List<ResultDto>): List<Result> {
        return if (resultsDto.isEmpty()) {
            emptyList()
        } else {
            resultsDto.map {
                mapResultDtoToResult(it)
            }
        }
    }

    private fun mapResultDtoToResult(resultDto: ResultDto): Result {
        with(resultDto) {
            return Result(
                uuid = uuid,
                level = level,
                sectionUuid = sectionUuid,
                averageCl = averageCl,
                averageCr = averageCr,
                averageClCr = averageClCr,
                shiftDeg = shiftDeg,
                shiftMm = shiftMm,
                tanAlpha = tanAlpha,
                distToMeasureLevel = distToMeasureLevel.toMmInt(),
                distDelta = distDelta.toMmInt(),
                betaAverageLeft = betaAverageLeft,
                betaAverageRight = betaAverageRight,
                betI = betI,
                betaDelta = betaDelta,
                measurementUuid = measurementUuid,
                measurementGroupUuid = measurementGroupUuid
            )
        }
    }

    private fun mapPhotosDtoToPhotos(photosDto: List<PhotoDto>): List<Photo> {
        return if (photosDto.isEmpty()) {
            emptyList()
        } else photosDto.map {
            mapPhotoDtoToPhoto(it)
        }
    }

    private fun mapPhotoDtoToPhoto(photoDto: PhotoDto): Photo {
        with(photoDto) {
            return Photo(
                uuid = uuid,
                name = name,
                date = date.toDate() ?: Date(),
                url = url,
                urlThumbnail = urlThumbnail,
                employeeId = employeeId,
                employeeName = employeeName,
                dimensionXPx = dimensions.getX(),
                dimensionYPx = dimensions.getY(),
                thumbnailDimXPx = thumbnailDim.getX(),
                thumbnailDimYPx = thumbnailDim.getY()
            )
        }
    }

    private fun mapSiteEquipmentsDtoToSiteEquipments(siteEquipmentsDto: List<SiteEquipmentDto>): List<SiteEquipment> {
        return if (siteEquipmentsDto.isEmpty()) {
            emptyList()
        } else siteEquipmentsDto.map {
            mapSiteEquipmentDtoToSiteEquipment(it)
        }
    }

    private fun mapSiteEquipmentDtoToSiteEquipment(siteEquipmentDto: SiteEquipmentDto): SiteEquipment {
        with(siteEquipmentDto) {
            return SiteEquipment(
                uuid = uuid,
                type = type,
                name = name
            )
        }
    }
}
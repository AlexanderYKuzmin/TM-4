package com.kuzmin.tm_4.data.remote.mapper

import com.kuzmin.tm_4.common.extension.getX
import com.kuzmin.tm_4.common.extension.getY
import com.kuzmin.tm_4.common.extension.toDate
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
import com.kuzmin.tm_4.feature.sites.domain.model.Tenant
import com.kuzmin.tm_4.feature.sites.domain.model.sites.Address
import com.kuzmin.tm_4.feature.sites.domain.model.sites.Construction
import com.kuzmin.tm_4.feature.sites.domain.model.sites.Group
import com.kuzmin.tm_4.feature.sites.domain.model.sites.Level
import com.kuzmin.tm_4.feature.sites.domain.model.sites.Measurement
import com.kuzmin.tm_4.feature.sites.domain.model.sites.MeasurementConstruction
import com.kuzmin.tm_4.feature.sites.domain.model.sites.Photo
import com.kuzmin.tm_4.feature.sites.domain.model.sites.Section
import com.kuzmin.tm_4.feature.sites.domain.model.sites.Site
import com.kuzmin.tm_4.feature.sites.domain.model.sites.SiteEquipment
import com.kuzmin.tm_4.feature.sites.domain.model.sites.SiteParams
import com.kuzmin.tm_4.feature.sites.domain.model.sites.Result
import java.util.Date

class SitesDtoToModelMapper {

    fun mapSitesDtoToSites(sitesDto: List<SiteDto>): List<Site> {
        return sitesDto.map {
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
            measurementsConstructions = mapMeasurementConstructionsDtoToMeasurementConstructions(siteDto.measurementsConstructionsDto),
            measurementsGroupsDto = mapMeasurementGroupsDtoToMeasurementGroups(siteDto.measurementsGroupsDto),
            measurements = mapMeasurementsDtoToMeasurements(siteDto.measurementsDto),
            results = mapResultsDtoToResults(siteDto.resultsDto)
        )
    }

    private fun mapSiteParamsDtoToSiteParams(siteParamsDto: SiteParamsDto): SiteParams {
        return SiteParams(
            remoteId = siteParamsDto.id,
            name = siteParamsDto.name,
            siteUuid = siteParamsDto.siteUuid,
            description = siteParamsDto.description,
            latitude = siteParamsDto.latitude,
            longitude = siteParamsDto.longitude,
            siteType = siteParamsDto.siteType,
            siteTypeDescription = siteParamsDto.siteTypeDescription
        )
    }
    private fun mapConstructionsDtoToConstructions(constructionsDto: List<ConstructionDto>): List<Construction> {
        return if (constructionsDto.isEmpty()) {
            emptyList()
        } else constructionsDto.map {
            mapConstructionDtoToConstruction(it)
        }
    }
    private fun mapConstructionDtoToConstruction(constructionDto: ConstructionDto): Construction {
        return Construction(
            uuid = constructionDto.uuid,
            version = constructionDto.version,
            description = constructionDto.description,
            status = constructionDto.status,
            numOfSections = constructionDto.numOfSections,
            height = constructionDto.height,
            constructionType = constructionDto.constructionType,
            config = constructionDto.config,
            measureLevels = constructionDto.measureLevels,
            siteUuid = constructionDto.siteUuid
        )
    }

    private fun mapConstructionsLevelsDtoToConstructionsLevels(constructionsLevelsDto: List<LevelDto>): List<Level> {
        return if (constructionsLevelsDto.isEmpty()) {
            emptyList()
        } else {
            constructionsLevelsDto.map { mapConstructionLevelDtoToConstructionLevel(it) }
        }
    }

    private fun mapConstructionLevelDtoToConstructionLevel(constructionLevelDto: LevelDto): Level {
        return Level(
            number = constructionLevelDto.number,
            position = constructionLevelDto.position,
            altitude = constructionLevelDto.altitude
        )
    }

    private fun mapConstructionsSectionsDtoToConstructionsSections(constructionsSectionsDto: List<SectionDto>): List<Section> {
        return if (constructionsSectionsDto.isEmpty()) {
            emptyList()
        } else {
            constructionsSectionsDto.map { mapConstructionSectionDtoToConstructionSection(it) }
        }
    }

    private fun mapConstructionSectionDtoToConstructionSection(constructionSectionDto: SectionDto): Section {
        return Section (
            uuid = constructionSectionDto.uuid,
            number = constructionSectionDto.number,
            wBottom = constructionSectionDto.wBottom,
            wTop = constructionSectionDto.wTop,
            height = constructionSectionDto.height,
            level = constructionSectionDto.level,
            status = constructionSectionDto.status,
            constructionUuid = constructionSectionDto.constructionUuid
        )
    }

    private fun mapTenantDtoToTenant(tenantDto: TenantDto): Tenant {
        return Tenant(
            name = tenantDto.name,
            logo = tenantDto.logo
        )
    }
    private fun mapAddressDtoToAddress(addressDto: AddressDto): Address {
        return Address(
            uuid = addressDto.uuid,
            country = addressDto.country,
            region = addressDto.region,
            regionCode = addressDto.regionCode,
            subRegion = addressDto.subRegion,
            city = addressDto.city,
            street = addressDto.street,
            building = addressDto.building,
            postalCode = addressDto.postalCode
        )
    }

    private fun mapMeasurementConstructionsDtoToMeasurementConstructions(
        measurementConstructionsDto: List<MeasurementConstructionDto>
    ): List<MeasurementConstruction> {
        return if (measurementConstructionsDto.isEmpty()) {
            emptyList()
        } else {
            measurementConstructionsDto.map {
                mapMeasurementConstructionDtoToMeasurementConstruction(it)
            }
        }
    }

    private fun mapMeasurementConstructionDtoToMeasurementConstruction(
        measurementConstructionDto: MeasurementConstructionDto
    ): MeasurementConstruction {
        return MeasurementConstruction(
            uuid = measurementConstructionDto.uuid,
            measurementName = measurementConstructionDto.measurementName,
            creator = measurementConstructionDto.creator,
            startLevel = measurementConstructionDto.startLevel,
            creationDate = measurementConstructionDto.creationDate.toDate() ?: Date(),
            completedDate = measurementConstructionDto.completedDate.toDate() ?: Date(),
            isCompleted = measurementConstructionDto.isCompleted,
            employee = measurementConstructionDto.employee,
            constructionUuid = measurementConstructionDto.constructionUuid,
            employeeName = measurementConstructionDto.employeeName,
            creatorName = measurementConstructionDto.creatorName
        )
    }

    private fun mapMeasurementGroupsDtoToMeasurementGroups(groupsDto: List<GroupDto>): List<Group> {
        return if (groupsDto.isEmpty()) {
            emptyList()
        } else {
            groupsDto.map { mapMeasurementGroupDtoToMeasurementGroup(it) }
        }
    }

    private fun mapMeasurementGroupDtoToMeasurementGroup(groupDto: GroupDto): Group {
        return Group(
            uuid = groupDto.uuid,
            measurementConstructionUuid = groupDto.measurementConstructionUuid,
            groupNum = groupDto.groupNum,
            azimuth = groupDto.azimuth,
            theoDistance = groupDto.theoDistance,
            theoHeight = groupDto.theoHeight,
        )
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
        return Result(
            uuid = resultDto.uuid,
            level = resultDto.level,
            sectionUuid = resultDto.sectionUuid,
            averageCl = resultDto.averageCl,
            averageCr = resultDto.averageCr,
            averageClCr = resultDto.averageClCr,
            shiftDeg = resultDto.shiftDeg,
            shiftMm = resultDto.shiftMm,
            tanAlpha = resultDto.tanAlpha,
            distToMeasureLevel = resultDto.distToMeasureLevel,
            distDelta = resultDto.distDelta,
            betaAverageLeft = resultDto.betaAverageLeft,
            betaAverageRight = resultDto.betaAverageRight,
            betI = resultDto.betI,
            betaDelta = resultDto.betaDelta,
            measurementUuid = resultDto.measurementUuid,
            measurementGroupUuid = resultDto.measurementGroupUuid
        )
    }

    private fun mapPhotosDtoToPhotos(photosDto: List<PhotoDto>): List<Photo> {
        return if (photosDto.isEmpty()) {
            emptyList()
        } else photosDto.map {
            mapPhotoDtoToPhoto(it)
        }
    }

    private fun mapPhotoDtoToPhoto(photoDto: PhotoDto): Photo {
        return Photo(
            uuid = photoDto.uuid,
            name = photoDto.name,
            date = photoDto.date.toDate(),
            url = photoDto.url,
            urlThumbnail = photoDto.urlThumbnail,
            employeeId = photoDto.employeeId,
            employeeName = photoDto.employeeName,
            dimensionXPx = photoDto.dimensions.getX(),
            dimensionYPx = photoDto.dimensions.getY(),
            thumbnailDimXPx = photoDto.thumbnailDim.getX(),
            thumbnailDimYPx = photoDto.thumbnailDim.getY()
        )
    }

    private fun mapSiteEquipmentsDtoToSiteEquipments(siteEquipmentsDto: List<SiteEquipmentDto>): List<SiteEquipment> {
        return if (siteEquipmentsDto.isEmpty()) {
            emptyList()
        } else siteEquipmentsDto.map {
            mapSiteEquipmentDtoToSiteEquipment(it)
        }
    }

    private fun mapSiteEquipmentDtoToSiteEquipment(siteEquipmentDto: SiteEquipmentDto): SiteEquipment {
        return SiteEquipment(
            uuid = siteEquipmentDto.uuid,
            type = siteEquipmentDto.type,
            name = siteEquipmentDto.name
        )
    }
}
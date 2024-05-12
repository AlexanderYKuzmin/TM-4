package com.kuzmin.tm_4.data.local.mapper

import com.kuzmin.tm_4.common.extension.toDate
import com.kuzmin.tm_4.core.database.delivery.SiteDb
import com.kuzmin.tm_4.core.database.model.site.AddressDb
import com.kuzmin.tm_4.core.database.model.site.ConstructionDb
import com.kuzmin.tm_4.core.database.model.site.GroupDb
import com.kuzmin.tm_4.core.database.model.site.MeasurementConstructionDb
import com.kuzmin.tm_4.core.database.model.site.MeasurementDb
import com.kuzmin.tm_4.core.database.model.site.PhotoDb
import com.kuzmin.tm_4.core.database.model.site.ResultDb
import com.kuzmin.tm_4.core.database.model.site.SectionDb
import com.kuzmin.tm_4.core.database.model.site.SiteEquipmentDb
import com.kuzmin.tm_4.core.database.model.site.SiteParamsDb
import com.kuzmin.tm_4.core.database.model.site.TenantDb
import com.kuzmin.tm_4.feature.api.domain.model.Tenant
import com.kuzmin.tm_4.feature.api.domain.model.site.Address
import com.kuzmin.tm_4.feature.api.domain.model.site.Construction
import com.kuzmin.tm_4.feature.api.domain.model.site.Group
import com.kuzmin.tm_4.feature.api.domain.model.site.Measurement
import com.kuzmin.tm_4.feature.api.domain.model.site.MeasurementConstruction
import com.kuzmin.tm_4.feature.api.domain.model.site.Photo
import com.kuzmin.tm_4.feature.api.domain.model.site.Result
import com.kuzmin.tm_4.feature.api.domain.model.site.Section
import com.kuzmin.tm_4.feature.api.domain.model.site.Site
import com.kuzmin.tm_4.feature.api.domain.model.site.SiteEquipment
import com.kuzmin.tm_4.feature.api.domain.model.site.SiteParams
import java.util.Date
import javax.inject.Inject

open class SiteDbToSiteModelMapper @Inject constructor(

){
    fun mapSiteDbToSiteModel(siteDb: SiteDb): Site {
        with(siteDb) {
            return Site(
                siteParams = mapSiteParamsDbToSiteParams(siteParamsDb),
                tenant = mapTenantDbToTenant(tenantDb),
                address = mapAddressDbToAddress(addressDb),
                photos = mapPhotosDbToPhotos(photos),
                siteEquipments = mapSiteEquipmentsDbToSiteEquipments(siteEquipments),
                constructions = mapConstructionListDbToConstructionList(constructions),
                constructionsLevels = null,
                constructionsSections = mapSectionListDbToSectionList(sections),
                measurementsConstructions = mapMeasurementConstructionListDbToMeasurementConstructionList(measurementsConstructions),
                measurementsGroups = mapGroupListDbToGroupList(groups),
                measurements = mapMeasurementListDbToMeasurementList(measurements),
                results = mapResultListDbToResultList(results)
            )
        }
    }

    private fun mapSiteParamsDbToSiteParams(spDb: SiteParamsDb): SiteParams {
        with(spDb) {
            return SiteParams(
                siteUuid = uuid,
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

    private fun mapTenantDbToTenant(tenantDb: TenantDb): Tenant {
        with(tenantDb) {
            return Tenant(
               uuid = uuid,
               name = name ?: "No name",
               logo = logo
            )
        }
    }

    private fun mapAddressDbToAddress(addressDb: AddressDb): Address {
        with(addressDb) {
            return Address(
                uuid = uuid,
                country = country,
                region = region,
                regionCode = regionCode ?: 0,
                subRegion = subRegion,
                city = city,
                street = street,
                building = building,
                postalCode = postalCode
            )
        }
    }

    private fun mapPhotosDbToPhotos(photoDbList: List<PhotoDb>): List<Photo> {
        if (photoDbList.isEmpty()) return emptyList()
        return photoDbList.map {
            mapPhotoDbToPhoto(it)
        }
    }

    private fun mapPhotoDbToPhoto(photoDb: PhotoDb): Photo {
        with(photoDb) {
            return Photo(
                uuid = uuid,
                name = name,
                date = date.toDate(),
                url = url,
                urlThumbnail = urlThumbnail,
                employeeUuid = null,
                employeeName =  employeeName,
                dimensionXPx = null,
                dimensionYPx = null,
                thumbnailDimXPx = null,
                thumbnailDimYPx = null
            )
        }
    }

    private fun mapSiteEquipmentsDbToSiteEquipments(siteEquipmentDbList: List<SiteEquipmentDb>): List<SiteEquipment> {
        if (siteEquipmentDbList.isEmpty()) return emptyList()
        return siteEquipmentDbList.map {
            mapSiteEquipmentDbToSiteEquipment(it)
        }
    }

    private fun mapSiteEquipmentDbToSiteEquipment(siteEquipmentDb: SiteEquipmentDb): SiteEquipment {
        with(siteEquipmentDb) {
            return SiteEquipment(
                uuid = uuid,
                type = type,
                name = name
            )
        }
    }

    private fun mapConstructionListDbToConstructionList(constructionDbList: List<ConstructionDb>): List<Construction> {
        if (constructionDbList.isEmpty()) throw RuntimeException("There is no construction in the site")
        return constructionDbList.map {
            mapConstructionDbToConstruction(it)
        }
    }

    fun mapConstructionDbToConstruction(constructionDb: ConstructionDb): Construction {
        with(constructionDb) {
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

    fun mapSectionListDbToSectionList(sectionDbList: List<SectionDb>): List<Section> {
        if (sectionDbList.isEmpty()) throw RuntimeException("There is no one section in the construction")
        return sectionDbList.map {
            mapSectionDbToSection(it)
        }
    }

    private fun mapSectionDbToSection(sectionDb: SectionDb): Section {
        with(sectionDb) {
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

    fun mapMeasurementConstructionListDbToMeasurementConstructionList(mcDbList: List<MeasurementConstructionDb>): List<MeasurementConstruction>? {
        if (mcDbList.isEmpty()) return emptyList()
        return mcDbList.map {
            mapMeasurementConstructionDbToMeasurementConstruction(it)
        }
    }

    fun mapMeasurementConstructionDbToMeasurementConstruction(mcDb: MeasurementConstructionDb): MeasurementConstruction {
        with(mcDb) {
            return MeasurementConstruction(
                uuid = uuid,
                measurementName = measurementName,
                creatorUuid = creatorUuid,
                startLevel = startLevel,
                creationDate = creationDate.toDate() ?: Date(),
                completedDate = completedDate.toDate(),
                isCompleted = isCompleted,
                employeeUuid = employeeUuid,
                constructionUuid = constructionUuid,
                employeeName = employeeName,
                creatorName = creatorName,
                isServiceable = isServiceable
            )
        }
    }

    fun mapGroupListDbToGroupList(groupDbList: List<GroupDb>): List<Group>? {
        if (groupDbList.isEmpty()) return emptyList()
        return groupDbList.map {
            mapGroupDbToGroup(it)
        }
    }

    fun mapGroupDbToGroup(groupDb: GroupDb): Group {
        with(groupDb) {
            return Group(
                uuid = uuid,
                groupNum = groupNum,
                azimuth = azimuth,
                theoDistance = theoDistance,
                theoHeight = theoHeight,
                measurementConstructionUuid = measurementConstructionUuid
            )
        }
    }

    fun mapMeasurementListDbToMeasurementList(measurementDbList: List<MeasurementDb>): List<Measurement>? {
        if (measurementDbList.isEmpty()) return emptyList()
        return measurementDbList.map {
            mapMeasurementDbToMeasurement(it)
        }
    }

    fun mapMeasurementDbToMeasurement(measurementDb: MeasurementDb): Measurement {
        with(measurementDb) {
            return Measurement(
                uuid = uuid,
                level = level,
                leftAngleCl = leftAngleCl,
                leftAngleCr = leftAngleCr,
                rightAngleCr = rightAngleCr,
                rightAngleCl = rightAngleCl,
                measurementGroupUuid = measurementGroupUuid
            )
        }
    }

    fun mapResultListDbToResultList(resultDbList: List<ResultDb>): List<Result>? {
        if (resultDbList.isEmpty()) return emptyList()
        return resultDbList.map {
            mapResultDbToResult(it)
        }
    }

    fun mapResultDbToResult(resultDb: ResultDb): Result {
        with(resultDb) {
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
                distToMeasureLevel = distToMeasureLevel,
                distDelta = distDelta,
                betaAverageLeft = betaAverageLeft,
                betaAverageRight = betaAverageRight,
                betI = betI,
                betaDelta = betaDelta,
                measurementUuid = measurementUuid,
                measurementGroupUuid = measurementGroupUuid
            )
        }
    }
}
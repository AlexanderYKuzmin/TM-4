package com.kuzmin.tm_4.core.database.util

import com.kuzmin.tm_4.common.util.CommonConstants
import com.kuzmin.tm_4.core.database.model.delivery.ConstructionAndSectionsDb
import com.kuzmin.tm_4.core.database.model.delivery.SiteDb
import com.kuzmin.tm_4.core.database.model.site.AddressDb
import com.kuzmin.tm_4.core.database.model.site.ConstructionDb
import com.kuzmin.tm_4.core.database.model.site.GroupDb
import com.kuzmin.tm_4.core.database.model.site.LevelDb
import com.kuzmin.tm_4.core.database.model.site.MeasurementConstructionDb
import com.kuzmin.tm_4.core.database.model.site.MeasurementDb
import com.kuzmin.tm_4.core.database.model.site.PhotoDb
import com.kuzmin.tm_4.core.database.model.site.ResultDb
import com.kuzmin.tm_4.core.database.model.site.SectionDb
import com.kuzmin.tm_4.core.database.model.site.SiteEquipmentDb
import com.kuzmin.tm_4.core.database.model.site.SiteParamsDb
import com.kuzmin.tm_4.core.database.model.site.TenantDb

object TmDatabaseTestUtil {
    /*fun createTestPhotoDbList(): List<PhotoDb> {
        return listOf(
            createPhotoDb(0),
            createPhotoDb(1)
        )
    }*/

    fun createTestSiteDbList(): List<SiteDb> {
        return listOf(
            createTestSite(0),
            createTestSite(1)
        )
    }

    fun createConstructionAndSections(): ConstructionAndSectionsDb {
        val construction = createConstructionDb("actual", 0, 0)
        val sections = createSections(0, listOf(construction))
        return ConstructionAndSectionsDb(
            construction,
            sections
        )
    }

    fun createTestBareSite(siteIndex: Int): SiteDb {
        return SiteDb(
            siteParamsDb = createSiteParamsDb(siteIndex),
            tenantDb = createTenantDb(siteIndex),
            addressDb = createAddressDb(siteIndex),
            siteEquipments = createSiteEquipmentsDb(),
            photos = createPhotos(),
            listOf(),
            listOf(),
            listOf(),
            listOf(),
            listOf(),
            listOf(),
            listOf()
        )
    }

    fun createTestSite(siteIndex: Int): SiteDb {
        return SiteDb(
            siteParamsDb = createSiteParamsDb(siteIndex),
            tenantDb = createTenantDb(siteIndex),
            addressDb = createAddressDb(siteIndex),
            siteEquipments = createSiteEquipmentsDb(),
            photos = createPhotos(),
            constructions = createConstructions(siteIndex),
            sections = createSections(siteIndex, createConstructions(siteIndex)),
            groups = createGroups(),
            measurementsConstructions = createMeasurementsConstructions(),
            measurements = createMeasurements(),
            results = createResults(),
            levelsInfo = createLevelsInfo()
        )
    }

    private fun createSiteParamsDb(index: Int): SiteParamsDb {
        return SiteParamsDb(
            uuid = "site$index",
            siteUuid = "site$index",
            name = "BS-TEST-DB-$index",
            description = "BS-TEST-DB",
            latitude = 10.5555,
            longitude = 100.5555,
            siteType = 11,
            siteTypeDescription = "TEST",
            durability = if (index < 1) CommonConstants.CONST else CommonConstants.TEMP
        )
    }

    private fun createTenantDb(index: Int): TenantDb {
        return TenantDb(
            uuid = "site$index-tenant0",
            name = "TEST-Tenant",
            logo = null,
            siteUuid = "site$index"
        )
    }

    private fun createAddressDb(index: Int): AddressDb {
        return AddressDb(
            uuid = "site${index}-address0",
            siteUuid = "site$index",
            country = "РФ-тест",
            region = "Коми тест",
            regionCode = null,
            subRegion = "Прилузский район",
            city = "Город",
            street = "",
            building = "",
            postalCode = "111222"
        )
    }

    private fun createSiteEquipmentsDb(): List<SiteEquipmentDb> {
        return listOf()
    }

    private fun createPhotos(): List<PhotoDb> {
        return listOf()
    }

    private fun createConstructions(siteIndex: Int): List<ConstructionDb> {
        return listOf(
            createConstructionDb("depricated", siteIndex, 0 ),
            createConstructionDb("actual", siteIndex, 1)
        )
    }

    private fun createConstructionDb(status: String, siteIndex: Int, constructionIndex: Int): ConstructionDb {
        return ConstructionDb(
            uuid = "site$siteIndex-construction$constructionIndex",
            version = 1,
            description = "Тест-конструкция-$constructionIndex",
            status = status,
            numOfSections = 5,
            height = 50,
            constructionType = "tower",
            config = "4",
            measureLevels = null,
            siteUuid = "site$siteIndex",
            cDate = "11.11.2011"
        )
    }

    private fun createSections(siteIndex: Int, constructions: List<ConstructionDb>): List<SectionDb> {
        val sections = mutableListOf<SectionDb>()
        constructions.forEach {
            for (i in 0 until it.numOfSections) {
                sections.add(
                    createSectionDb(
                        sUuid= "site$siteIndex",
                        cUuid = it.uuid,
                        number = i + 1,
                        status = it.status ,
                        suffix = "section$i"
                    )
                )
            }
        }
        return sections
    }

    private fun createSectionDb(sUuid: String, cUuid: String, number: Int, status: String, suffix: String): SectionDb {
        return SectionDb(
            uuid = "$cUuid-$suffix",
            siteUuid = sUuid,
            constructionUuid = cUuid,
            number = number,
            wBottom = 700,
            wTop = 700,
            height = 10000,
            level = null,
            status = status
        )
    }

    private fun createGroups(): List<GroupDb> {
        return listOf()
    }

    private fun createMeasurementsConstructions(): List<MeasurementConstructionDb> {
        return listOf()
    }

    private fun createMeasurements(): List<MeasurementDb> {
        return listOf()
    }

    private fun createResults(): List<ResultDb> {
        return listOf()
    }

    private fun createLevelsInfo(): List<LevelDb> {
        return listOf()
    }
}
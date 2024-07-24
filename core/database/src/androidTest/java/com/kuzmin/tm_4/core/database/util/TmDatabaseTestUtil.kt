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

    const val TEST_SITE_UUID = "4758c533-70d3-4429-8c6e-661617958b44"

    const val TEST_SINGLE_CONSTRUCTION_UUID = "adff1f64-09bc-4d11-be88-000000000001"

    fun createConstructionAndSections(): ConstructionAndSectionsDb {
        val construction = createConstructionDb("actual", "000000000001")
        val sections = createSections(listOf(construction))
        return ConstructionAndSectionsDb(
            construction,
            sections
        )
    }

    fun createTestBareSite(): SiteDb {
        return SiteDb(
            siteParamsDb = createSiteParamsDb(),
            tenantDb = createTenantDb(),
            addressDb = createAddressDb(),
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

    fun createTestSite(): SiteDb {
        return SiteDb(
            siteParamsDb = createSiteParamsDb(),
            tenantDb = createTenantDb(),
            addressDb = createAddressDb(),
            siteEquipments = createSiteEquipmentsDb(),
            photos = createPhotos(),
            constructions = createConstructions(),
            sections = createSections(createConstructions()),
            groups = createGroups(),
            measurementsConstructions = createMeasurementsConstructions(),
            measurements = createMeasurements(),
            results = createResults(),
            levelsInfo = createLevelsInfo()
        )
    }

    private fun createSiteParamsDb(): SiteParamsDb {
        return SiteParamsDb(
            uuid = "4758c533-70d3-4429-8c6e-661617958b44",
            siteUuid = "4758c533-70d3-4429-8c6e-661617958b44",
            name = "BS-TEST-DB",
            description = "BS-TEST-DB",
            latitude = 10.5555,
            longitude = 100.5555,
            siteType = 11,
            siteTypeDescription = "TEST",
            durability = CommonConstants.CONST
        )
    }

    private fun createTenantDb(): TenantDb {
        return TenantDb(
            uuid = "3462ab26-8b23-4021-afd1-7f76cc02e796",
            name = "TEST-Tenant",
            logo = null,
            siteUuid = "4758c533-70d3-4429-8c6e-661617958b44"
        )
    }

    private fun createAddressDb(): AddressDb {
        return AddressDb(
            uuid = "e3bf9611-dbd4-45f4-b31c-59a72b8e2905",
            siteUuid = "4758c533-70d3-4429-8c6e-661617958b44",
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

    private fun createConstructions(): List<ConstructionDb> {
        return listOf(
            createConstructionDb("depricated", "000000000000"),
            createConstructionDb("actual", "000000000001")
        )
    }

    private fun createConstructionDb(status: String, suffix: String): ConstructionDb {
        return ConstructionDb(
            uuid = "adff1f64-09bc-4d11-be88-$suffix",
            version = 1,
            description = "Тест-конструкция",
            status = status,
            numOfSections = 5,
            height = 50,
            constructionType = "tower",
            config = "4",
            measureLevels = null,
            siteUuid = "4758c533-70d3-4429-8c6e-661617958b44"
        )
    }

    private fun createSections(constructions: List<ConstructionDb>): List<SectionDb> {
        val sections = mutableListOf<SectionDb>()
        constructions.forEach {
            val cSuf = it.uuid.last()
            for (i in 0 until it.numOfSections) {
                sections.add(
                    createSectionDb(
                        cUuid = it.uuid,
                        number = i + 1,
                        status = it.status ,
                        suffix = "0000000000$cSuf$i"
                    )
                )
            }
        }
        return sections
    }

    private fun createSectionDb(cUuid: String, number: Int, status: String, suffix: String): SectionDb {
        return SectionDb(
            uuid = "09e1e378-d030-4fb9-931c-$suffix",
            siteUuid = "4758c533-70d3-4429-8c6e-661617958b44",
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
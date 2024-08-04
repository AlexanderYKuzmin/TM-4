package com.kuzmin.tm_4.data.local.util

import com.kuzmin.tm_4.core.database.model.delivery.SiteDb
import com.kuzmin.tm_4.core.database.model.site.GroupDb
import com.kuzmin.tm_4.core.database.model.site.LevelDb
import com.kuzmin.tm_4.core.database.model.site.MeasurementDb
import com.kuzmin.tm_4.core.database.model.site.ResultDb
import com.kuzmin.tm_4.data.local.util.Constants.SITE_PREFIX
import com.kuzmin.tm_4.feature.api.domain.model.sample.SiteSample
import com.kuzmin.tm_4.feature.api.domain.model.site.Group
import com.kuzmin.tm_4.feature.api.domain.model.site.Site

object SiteTestData {

    fun createTestSiteList(): List<Site> {
        return listOf(
            createTestSite(0),
            createTestSite(1)
        )
    }

    fun createTestSite(sIndex: Int): Site {
        return Site(
            siteParams = SiteParamsTestData.createTestSiteParams(sIndex),
            tenant = TenantTestData.createTestTenant(sIndex),
            address = AddressTestData.createTestAddress(sIndex),
            photos = emptyList(),
            siteEquipments = emptyList(),
            constructions = ConstructionTestData.createTestConstructionList(sIndex, 2),
            constructionsLevels = null,
            constructionsSections = SectionTestData.createTestSectionList(
                sIndex,
                ConstructionTestData.createTestConstructionList(sIndex, 2).map { it.uuid.last().digitToInt() },
                3,
                "actual"
                ),
            measurementsConstructions = null,
            measurementsGroups = null,
            measurements = null,
            results = null,
            levelsInfo = null
        )
    }


    fun createSiteDbList(): List<SiteDb> {
        return listOf(
            createTestSiteDb(0),
            createTestSiteDb(1)
        )
    }

    fun createTestSiteDb(sIndex:Int): SiteDb {
        return SiteDb(
            siteParamsDb = SiteParamsTestData.createTestSiteParamsDb(sIndex),
            tenantDb = TenantTestData.createTestTenantDb(sIndex),
            addressDb = AddressTestData.createTestAddressDb(sIndex),
            photos = emptyList(),
            siteEquipments = emptyList(),
            constructions = ConstructionTestData.createTestConstructionDbList(sIndex, 2),
            sections = SectionTestData.createTestSectionDbList(
                sIndex,
                ConstructionTestData.createTestConstructionList(sIndex, 2).map { it.uuid.last().digitToInt() },
                3,
                "actual"
            ),
            measurementsConstructions = listOf(),
            groups = listOf<GroupDb>(),
            measurements = listOf<MeasurementDb>(),
            results = listOf<ResultDb>(),
            levelsInfo = listOf<LevelDb>()
        )
    }

    fun createTestSiteSampleList(): List<SiteSample> {
        return listOf(
            createTestSiteSample(0),
            createTestSiteSample(1)
        )
    }


    fun createTestSiteSample(sIndex: Int): SiteSample {
        return SiteSample(
            uuid = "$SITE_PREFIX$sIndex",
            name = Constants.SITE_NAME,
            type = Constants.SITE_TYPE,
            typeDescription = Constants.SITE_TYPE_DESC,
            description = Constants.SITE_DESCRIPTION,
            photoUrl = null,
            photoDimension = null,
            tenant = TenantTestData.createTestTenant(sIndex),
            latitude = Constants.LATITUDE,
            longitude = Constants.LONGITUDE,
            address = AddressTestData.createTestAddressSample(sIndex),
            constructionsSample = ConstructionTestData.createTestConstructionSampleList(sIndex, 2)
        )
    }
}
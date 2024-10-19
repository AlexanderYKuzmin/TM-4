package com.kuzmin.tm_4.data.local.mapper.fromDbToModel

import com.kuzmin.tm_4.core.database.model.delivery.SiteDb
import com.kuzmin.tm_4.data.local.util.SiteTestData
import com.kuzmin.tm_4.feature.api.domain.model.site_related_model.site.sample.SiteSample
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class SiteDbToSiteSampleTest {

    private val mapper: SiteDbToSiteSampleMapper = SiteDbToSiteSampleMapper()

    private var siteSampleList: List<SiteSample>? = null

    private var siteDbList: List<SiteDb>? = null

    @Before
    fun setUp() {
        siteSampleList = SiteTestData.createTestSiteSampleList()

        siteDbList = SiteTestData.createSiteDbList()
    }

    @Test
    fun mapSiteDbListToSiteSamples() {
        val expected = siteSampleList

        val actual = mapper.mapSiteDbListToSiteSampleList(siteDbList!!)

        assertEquals(expected, actual)
    }
}
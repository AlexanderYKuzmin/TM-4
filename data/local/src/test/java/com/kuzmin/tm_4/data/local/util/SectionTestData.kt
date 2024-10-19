package com.kuzmin.tm_4.data.local.util

import com.kuzmin.tm_4.core.database.model.site.SectionDb
import com.kuzmin.tm_4.data.local.util.Constants.CONSTRUCTION_PREFIX
import com.kuzmin.tm_4.data.local.util.Constants.SECTION_PREFIX
import com.kuzmin.tm_4.data.local.util.Constants.SITE_PREFIX
import com.kuzmin.tm_4.feature.api.domain.model.site_related_model.site.Section

object SectionTestData {

    fun createTestSectionList(sIndex: Int, cIndexes: List<Int>, quantity: Int, status: String): List<Section> {
        val sections = listOf<Section>()
        for (cIndex in cIndexes) {
            for (i in 0 until quantity) {
                createTestSection(
                    sIndex,
                    cIndex,
                    i,
                    status
                )
            }
        }
        return sections
    }

    private fun createTestSection(sIndex: Int, cIndex: Int, secIndex: Int, status: String): Section {
        return Section(
            uuid = "$SITE_PREFIX$sIndex-$CONSTRUCTION_PREFIX$cIndex-$SECTION_PREFIX$secIndex",
            number = secIndex + 1,
            wBottom = 1000,
            wTop = 1000,
            height = 10000,
            level = null,
            status = status,
            constructionUuid = "$SITE_PREFIX$sIndex-$CONSTRUCTION_PREFIX$cIndex"
        )
    }

    fun createTestSectionDbList(sIndex: Int, cIndexes: List<Int>, quantity: Int, status: String): List<SectionDb> {
        val sectionsDb = listOf<SectionDb>()
        for (cIndex in cIndexes) {
            for (i in 0 until quantity) {
                createTestSectionDb(
                    sIndex,
                    cIndex,
                    i,
                    status
                )
            }
        }
        return sectionsDb
    }

    private fun createTestSectionDb(sIndex: Int, cIndex: Int, secIndex: Int, status: String): SectionDb {
        return SectionDb(
            uuid = "$SITE_PREFIX$sIndex-$CONSTRUCTION_PREFIX$cIndex-$SECTION_PREFIX$secIndex",
            siteUuid = "$SITE_PREFIX$sIndex",
            constructionUuid = "$SITE_PREFIX$sIndex-$CONSTRUCTION_PREFIX$cIndex",
            number = secIndex + 1,
            wBottom = 1000,
            wTop = 1000,
            height = 10000,
            level = null,
            status = status
        )
    }
}
package com.kuzmin.tm_4.data.local.util

import com.kuzmin.tm_4.core.database.model.site.SectionDb
import com.kuzmin.tm_4.feature.api.domain.model.site.Section

object SectionTestData {

    private const val SECTION_BASE_UUID = "e74f213d-9a4b-4bf2-91e1-00000000000"

    fun createTestSections(): List<Section> {
        val sections = listOf<Section>()
        for (i in 0..3) {
            createTestSection(
                SECTION_BASE_UUID + "$i",
                i
            )
        }
        return sections
    }

    private fun createTestSection(sectionUuid: String, i: Int): Section {
        return Section(
            uuid = sectionUuid,
            number = i + 1,
            wBottom = 1000,
            wTop = 1000,
            height = 10000,
            level = null,
            status = "actual",
            constructionUuid = ConstructionTestData.CONSTRUCTION_UUID
        )
    }

    fun createTestSectionsDb(): List<SectionDb> {
        val sectionsDb = listOf<SectionDb>()
        for (i in 0..3) {
            createTestSectionDb(
                SECTION_BASE_UUID + "$i",
                i
            )
        }
        return sectionsDb
    }

    private fun createTestSectionDb(sectionUuid: String, i: Int): SectionDb {
        return SectionDb(
            uuid = sectionUuid,
            siteUuid = ConstructionTestData.SITE_UUID,
            constructionUuid = ConstructionTestData.CONSTRUCTION_UUID,
            number = i + 1,
            wBottom = 1000,
            wTop = 1000,
            height = 10000,
            level = null,
            status = "actual"
        )
    }
}
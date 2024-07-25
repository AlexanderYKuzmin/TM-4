package com.kuzmin.tm_4.data.local.util

import com.kuzmin.tm_4.core.database.model.site.ConstructionDb
import com.kuzmin.tm_4.feature.api.domain.model.site.Construction

object ConstructionTestData {

    const val CONSTRUCTION_UUID = "e74f213d-9a4b-4bf2-91e1-abcbea81c026"
    const val SITE_UUID = "e74f213d-9a4b-4bf2-91e1-000000000000"

    fun createTestConstruction(): Construction {
        return Construction(
            uuid = CONSTRUCTION_UUID,
            version = 1,
            description = "TEST description",
            status = "actual",
            numOfSections = 4,
            height = 40000,
            constructionType = "tower",
            config = "4",
            measureLevels = null,
            siteUuid = SITE_UUID
        )
    }

    fun createTestConstructionDb(): ConstructionDb {
        return ConstructionDb(
            uuid = CONSTRUCTION_UUID,
            version = 1,
            description = "TEST description",
            status = "actual",
            numOfSections = 4,
            height = 40000,
            constructionType = "tower",
            config = "4",
            measureLevels = null,
            siteUuid = SITE_UUID
        )
    }
}
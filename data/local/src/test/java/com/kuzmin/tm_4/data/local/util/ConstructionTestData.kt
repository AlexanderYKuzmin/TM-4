package com.kuzmin.tm_4.data.local.util

import com.kuzmin.tm_4.core.database.model.site.ConstructionDb
import com.kuzmin.tm_4.data.local.util.Constants.CONFIG
import com.kuzmin.tm_4.data.local.util.Constants.CONSTRUCTION_DESCRIPTION
import com.kuzmin.tm_4.data.local.util.Constants.CONSTRUCTION_PREFIX
import com.kuzmin.tm_4.data.local.util.Constants.CONSTRUCTION_STATUS
import com.kuzmin.tm_4.data.local.util.Constants.CONSTRUCTION_TYPE
import com.kuzmin.tm_4.data.local.util.Constants.CONSTRUCTION_VERSION
import com.kuzmin.tm_4.data.local.util.Constants.C_DATE
import com.kuzmin.tm_4.data.local.util.Constants.HEIGHT
import com.kuzmin.tm_4.data.local.util.Constants.NUM_OF_SECTION
import com.kuzmin.tm_4.data.local.util.Constants.SITE_PREFIX
import com.kuzmin.tm_4.feature.api.domain.model.site_related_model.site.sample.ConstructionSample
import com.kuzmin.tm_4.feature.api.domain.model.site_related_model.site.Construction
import java.util.Date

object ConstructionTestData {

    fun createTestConstructionList(sIndex: Int, quantity: Int): List<Construction> {
        val constructionList = mutableListOf<Construction>()
        for (i in 0 until quantity) {
            constructionList.add(
                createTestConstruction(sIndex, i)
            )
        }
        return constructionList
    }

    fun createTestConstruction(sIndex: Int, cIndex: Int): Construction {
        return Construction(
            uuid = "$SITE_PREFIX$sIndex-$CONSTRUCTION_PREFIX$cIndex",
            version = CONSTRUCTION_VERSION,
            description = CONSTRUCTION_DESCRIPTION,
            status = CONSTRUCTION_STATUS,
            numOfSections = NUM_OF_SECTION,
            height = HEIGHT,
            constructionType = CONSTRUCTION_TYPE,
            config = CONFIG,
            measureLevels = null,
            siteUuid = "$SITE_PREFIX$sIndex",
            cDate = Date(1709310300000) //01.03.2024
        )
    }

    fun createTestConstructionDbList(sIndex: Int, quantity: Int): List<ConstructionDb> {
        val constructionList = mutableListOf<ConstructionDb>()
        for (i in 0 until quantity) {
            constructionList.add(
                createTestConstructionDb(sIndex, i)
            )
        }
        return constructionList
    }

    fun createTestConstructionDb(sIndex: Int, cIndex: Int): ConstructionDb {
        return ConstructionDb(
            uuid = "$SITE_PREFIX$sIndex-$CONSTRUCTION_PREFIX$cIndex",
            version = CONSTRUCTION_VERSION,
            description = CONSTRUCTION_DESCRIPTION,
            status = CONSTRUCTION_STATUS,
            numOfSections = NUM_OF_SECTION,
            height = HEIGHT,
            constructionType = CONSTRUCTION_TYPE,
            config = CONFIG,
            measureLevels = null,
            siteUuid = "$SITE_PREFIX$sIndex",
            cDate = C_DATE
        )
    }

    fun createTestConstructionSampleList(sIndex: Int, quantity: Int): List<ConstructionSample> {
        val constructionSampleList = mutableListOf<ConstructionSample>()
        for (i in 0 until quantity) {
            constructionSampleList.add(
                createTestConstructionSample(sIndex, i)
            )
        }
        return constructionSampleList
    }

    fun createTestConstructionSample(sIndex: Int, cIndex: Int): ConstructionSample {
        return ConstructionSample(
            uuid = "$SITE_PREFIX$sIndex-$CONSTRUCTION_PREFIX$cIndex",
            constructionType = CONSTRUCTION_TYPE,
            config = CONFIG,
            heightMm = HEIGHT,
            creationDate = Date(1709236800000), //01.03.2024,
            completedDate = null,
            isCompleted = false
        )
    }
}
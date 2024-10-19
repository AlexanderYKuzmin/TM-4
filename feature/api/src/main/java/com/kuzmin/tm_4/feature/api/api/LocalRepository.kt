package com.kuzmin.tm_4.feature.api.api

import com.kuzmin.tm_4.feature.api.domain.model.site_related_model.site.model_complex.ConstructionAndSections
import com.kuzmin.tm_4.feature.api.domain.model.site_related_model.site.model_complex.ConstructionFull
import com.kuzmin.tm_4.feature.api.domain.model.site_related_model.site.model_complex.GroupFull
import com.kuzmin.tm_4.feature.api.domain.model.site_related_model.site.model_complex.McAndConstruction
import com.kuzmin.tm_4.feature.api.domain.model.site_related_model.site.model_complex.McFull
import com.kuzmin.tm_4.feature.api.domain.model.site_related_model.site.sample.SiteSample
import com.kuzmin.tm_4.feature.api.domain.model.site_related_model.site.Construction
import com.kuzmin.tm_4.feature.api.domain.model.site_related_model.site.MeasurementConstruction
import com.kuzmin.tm_4.feature.api.domain.model.site_related_model.site.Photo
import com.kuzmin.tm_4.feature.api.domain.model.site_related_model.site.Site

interface LocalRepository {
    suspend fun addSiteToDb(site: Site, durability: String)

    suspend fun addConstructionToDb(construction: Construction)

    suspend fun addConstructionAndSectionsToDb(constructionAndSections: ConstructionAndSections)

    suspend fun getAllSiteSamples(): List<SiteSample>

    suspend fun getAllPhotoSamples(): List<Photo>

    suspend fun getSite(siteUuid: String): Site?

    suspend fun getConstructionFull(cUuid: String): ConstructionFull?

    suspend fun getConstruction(cUuid: String): Construction?

    suspend fun getAllMcFullByConstruction(sUuid: String, cUuid: String): List<McFull>?

    suspend fun getMcAndConstruction(mcUuid: String): McAndConstruction?

    suspend fun getMc(mcUuid: String): MeasurementConstruction?

    suspend fun getMcFull(mcUuid: String): McFull?

    suspend fun getGroupFull(groupMum: Int, mcUuid: String): GroupFull?

    suspend fun deleteAllTempSites()
}
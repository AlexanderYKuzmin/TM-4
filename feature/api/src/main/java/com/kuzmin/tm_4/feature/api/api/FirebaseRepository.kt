package com.kuzmin.tm_4.feature.api.api

import com.kuzmin.tm_4.feature.api.domain.model.site_related_model.site.sample.SiteSample
import com.kuzmin.tm_4.feature.api.domain.model.site_related_model.site.MeasurementConstruction
import com.kuzmin.tm_4.feature.api.domain.model.site_related_model.site.Photo
import com.kuzmin.tm_4.feature.api.domain.model.site_related_model.site.Site

interface FirebaseRepository {
    suspend fun getAllSiteSamples(): List<SiteSample>

    suspend fun getSitesByName(name: String): List<SiteSample>

    suspend fun getSiteById(uuid: String): Site

    suspend fun getAllPhotoSamples(): Map<String, String>

    suspend fun getSitePhotos(sUuid: String): List<Photo>

    suspend fun getSiteByIdNoSections(sUuid: String, cUuid: String): Site

    suspend fun getMeasurementConstructionsBySiteId(
        sUuid: String,
        cUuid: String
    ): List<MeasurementConstruction>
}
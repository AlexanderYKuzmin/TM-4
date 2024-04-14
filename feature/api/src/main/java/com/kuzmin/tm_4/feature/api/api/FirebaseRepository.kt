package com.kuzmin.tm_4.feature.api.api

import com.kuzmin.tm_4.feature.api.model.sample.SiteSample
import com.kuzmin.tm_4.feature.api.model.site.Site
import kotlinx.coroutines.flow.Flow

interface FirebaseRepository {
    suspend fun getAllSiteSamples(): List<SiteSample>

    suspend fun getSitesByName(name: String): List<SiteSample>

    suspend fun getSiteById(uuid: String): List<Site>

    suspend fun getAllPhotoSamples(): Map<String, String>
}
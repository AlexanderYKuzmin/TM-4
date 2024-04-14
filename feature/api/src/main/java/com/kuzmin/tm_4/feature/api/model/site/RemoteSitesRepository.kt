package com.kuzmin.tm_4.feature.api.model.site

import com.kuzmin.tm_4.feature.api.model.sample.SiteSample


interface RemoteSitesRepository {
    suspend fun getAllSites(): List<SiteSample>

    suspend fun getSitesById(ids: List<Long>): List<Site>

    suspend fun getSitesByName(name: String): List<SiteSample>
}
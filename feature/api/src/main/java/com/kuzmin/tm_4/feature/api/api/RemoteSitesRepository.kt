package com.kuzmin.tm_4.feature.api.api

import com.kuzmin.tm_4.feature.api.model.sample.SiteSample
import com.kuzmin.tm_4.feature.api.model.site.Site


interface RemoteSitesRepository {
    suspend fun getAllSites(): List<SiteSample>

    suspend fun getSitesById(ids: List<Long>): List<Site>

    suspend fun getSitesByName(name: String): List<SiteSample>
}
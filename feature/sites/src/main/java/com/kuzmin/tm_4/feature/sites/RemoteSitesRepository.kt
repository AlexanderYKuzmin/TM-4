package com.kuzmin.tm_4.feature.sites

import com.kuzmin.tm_4.feature.sites.domain.model.sites.Site
import com.kuzmin.tm_4.feature.sites.domain.model.samples.SiteSample

interface RemoteSitesRepository {
    suspend fun getAllSites(): List<SiteSample>

    suspend fun getSiteById(id: String): List<Site>

    suspend fun getSiteByName(name: String): List<Site>
}
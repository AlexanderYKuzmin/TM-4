package com.kuzmin.tm_4.data.remote.repository

import com.kuzmin.tm_4.core.network.ApiService
import com.kuzmin.tm_4.core.network.RequestInterceptor
import com.kuzmin.tm_4.data.remote.mapper.SitesDtoToModelMapper
import com.kuzmin.tm_4.data.remote.mapper.SitesSamplesDtoToModelMapper
import com.kuzmin.tm_4.feature.sites.RemoteSitesRepository
import com.kuzmin.tm_4.feature.sites.domain.model.samples.SiteSample
import com.kuzmin.tm_4.feature.sites.domain.model.sites.Site
import javax.inject.Inject

class RemoteSitesRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val sampleSitesSamplesDtoToModelMapper: SitesSamplesDtoToModelMapper,
    private val sitesDtoToModelMapper: SitesDtoToModelMapper
) : RemoteSitesRepository {
    override suspend fun getAllSites(): List<SiteSample> {
        TODO()
    }

    override suspend fun getSiteById(id: String): List<Site> {
        TODO("Not yet implemented")
    }

    override suspend fun getSiteByName(name: String): List<Site> {
        TODO("Not yet implemented")
    }
}
package com.kuzmin.tm_4.data.remote.repository

import android.util.Log
import com.kuzmin.tm_4.core.network.ApiService
import com.kuzmin.tm_4.core.network.RequestInterceptor
import com.kuzmin.tm_4.core.network.TokenContainer
import com.kuzmin.tm_4.data.remote.mapper.SitesDtoToModelMapper
import com.kuzmin.tm_4.data.remote.mapper.SitesSamplesDtoToModelMapper
import com.kuzmin.tm_4.feature.login.domain.AuthManager
import com.kuzmin.tm_4.feature.sites.RemoteSitesRepository
import com.kuzmin.tm_4.feature.sites.domain.model.samples.SiteSample
import com.kuzmin.tm_4.feature.sites.domain.model.sites.Site
import javax.inject.Inject

class RemoteSitesRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val sitesSamplesDtoToModelMapper: SitesSamplesDtoToModelMapper,
    private val sitesDtoToModelMapper: SitesDtoToModelMapper,
    private val authManager: AuthManager,
    private val requestInterceptor: RequestInterceptor
) : RemoteSitesRepository {

    override suspend fun getAllSites(): List<SiteSample> {
        Log.d("MainActivity", "Repository. Get all sites.")
        return sitesSamplesDtoToModelMapper
            .mapSitesSampleDtoToSitesSample(
                apiService.getAllSites(authManager.getToken()).sitesSampleDto
            ) ?: emptyList()
    }

    override suspend fun getSitesById(id: String): List<Site> {
        TODO("Not yet implemented")
    }

    override suspend fun getSitesByName(name: String): List<SiteSample> {
        Log.d("MainActivity", "Repository. Get sites by name.")
        return sitesSamplesDtoToModelMapper.mapSitesSampleDtoToSitesSample(
            apiService.getSitesByName(authManager.getToken(), name).sitesSampleDto
        ) ?: emptyList()
    }
}
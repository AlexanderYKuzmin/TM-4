package com.kuzmin.tm_4.data.remote.repository

import android.util.Log
import com.kuzmin.tm_4.core.network.ApiService
import com.kuzmin.tm_4.data.remote.mapper.SitesDtoToModelMapper
import com.kuzmin.tm_4.data.remote.mapper.SitesSamplesDtoToModelMapper
import com.kuzmin.tm_4.feature.login.domain.AuthManager
import com.kuzmin.tm_4.feature.api.api.RemoteSitesRepository
import com.kuzmin.tm_4.feature.api.domain.model.sample.SiteSample
import com.kuzmin.tm_4.feature.api.domain.model.site.Site
import javax.inject.Inject

class RemoteSitesRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val sitesSamplesDtoToModelMapper: SitesSamplesDtoToModelMapper,
    private val sitesDtoToModelMapper: SitesDtoToModelMapper,
    private val authManager: AuthManager,
) : RemoteSitesRepository {


    override suspend fun getAllSites(): List<SiteSample> {
        Log.d("MainActivity", "Repository. Get all sites.")
        Log.d("MainActivity", "AuthManager token: ${authManager.getToken()}")
        return sitesSamplesDtoToModelMapper
            .mapSitesSampleDtoToSitesSample(
                apiService.getAllSites(authManager.getToken()).sitesSampleDto
            ) ?: emptyList()
    }

    override suspend fun getSitesById(ids: List<Long>): List<Site> {
        val idsString = ids.joinToString(",")
        Log.d("Site", "ids string $idsString")
        Log.d("Site", "GET SITES BY ID ${
            apiService.getSitesById(authManager.getToken(), idsString).sitesDto?.get(0)?.measurementsConstructionsDto?.size
        }")
        return sitesDtoToModelMapper.mapSitesDtoToSites(
            apiService.getSitesById(authManager.getToken(), idsString).sitesDto
        ) ?: emptyList()
    }

    override suspend fun getSitesByName(name: String): List<SiteSample> {
        Log.d("MainActivity", "Repository. Get sites by name.")
        return sitesSamplesDtoToModelMapper.mapSitesSampleDtoToSitesSample(
            apiService.getSitesByName(authManager.getToken(), name).sitesSampleDto
        ) ?: emptyList()
    }
}
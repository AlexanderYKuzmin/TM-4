package com.kuzmin.tm_4.data.remote_fb.repo

import android.util.Log
import com.kuzmin.tm_4.core.network_fb.FirebaseService
import com.kuzmin.tm_4.core.network_fb.model.ConstructionFbDto
import com.kuzmin.tm_4.core.network_fb.model.SiteFbDto
import com.kuzmin.tm_4.data.remote_fb.mapper.PhotoMapper
import com.kuzmin.tm_4.data.remote_fb.mapper.SiteFbDtoToSiteMapper
import com.kuzmin.tm_4.feature.api.api.FirebaseRepository
import com.kuzmin.tm_4.feature.api.model.sample.SiteSample
import com.kuzmin.tm_4.feature.api.model.site.Site
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class FirebaseRepositoryImpl @Inject constructor(
    private val firebaseService: FirebaseService,
    private val mapper: SiteFbDtoToSiteMapper,
    private val photoMapper: PhotoMapper
) : FirebaseRepository {
    override suspend fun getAllSiteSamples(): List<SiteSample> {
        Log.d("getAll", "get all sites")

        return mapper.mapSiteFbDtoMapToSiteSampleModelList(
            firebaseService.getAllSites()
                .entries
                .associate { entry ->
                    entry.key.toObject(SiteFbDto::class.java) to
                            entry.value.map { it.toObject(ConstructionFbDto::class.java) }
                }
        )
    }

    override suspend fun getSitesByName(name: String): List<SiteSample> {
        TODO("Not yet implemented")
    }

    override suspend fun getSiteById(uuid: String): List<Site> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllPhotoSamples(): Map<String, String> {
        val photoItems = firebaseService.getAllPhotoSamples().items

        return photoMapper.mapListResultToPhotoUrlMap(photoItems)
    }
}
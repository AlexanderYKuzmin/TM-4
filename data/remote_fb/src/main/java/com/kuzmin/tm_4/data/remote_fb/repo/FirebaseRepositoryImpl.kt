package com.kuzmin.tm_4.data.remote_fb.repo

import android.util.Log
import com.kuzmin.tm_4.core.network_fb.FirebaseService
import com.kuzmin.tm_4.core.network_fb.model.site.ConstructionFbDto
import com.kuzmin.tm_4.core.network_fb.model.site.GroupFbDto
import com.kuzmin.tm_4.core.network_fb.model.site.MeasurementConstructionFbDto
import com.kuzmin.tm_4.core.network_fb.model.site.MeasurementFbDto
import com.kuzmin.tm_4.core.network_fb.model.site.ResultFbDto
import com.kuzmin.tm_4.core.network_fb.model.site.SectionFbDto
import com.kuzmin.tm_4.core.network_fb.model.site.SiteFbDto
import com.kuzmin.tm_4.data.remote_fb.mapper.site.PhotoMapper
import com.kuzmin.tm_4.data.remote_fb.mapper.site.SiteDataFbDtoToSiteModelMapper
import com.kuzmin.tm_4.data.remote_fb.mapper.site.SiteFbDtoToSiteSampleMapper
import com.kuzmin.tm_4.data.remote_fb.model_fb.site.SiteDataFbDto
import com.kuzmin.tm_4.feature.api.api.FirebaseRepository
import com.kuzmin.tm_4.feature.api.domain.model.site_related_model.site.sample.SiteSample
import com.kuzmin.tm_4.feature.api.domain.model.site_related_model.site.MeasurementConstruction
import com.kuzmin.tm_4.feature.api.domain.model.site_related_model.site.Photo
import com.kuzmin.tm_4.feature.api.domain.model.site_related_model.site.Site
import javax.inject.Inject

class FirebaseRepositoryImpl @Inject constructor(
    private val firebaseService: FirebaseService,
    private val mapper: SiteFbDtoToSiteSampleMapper,
    private val siteMapper: SiteDataFbDtoToSiteModelMapper,
    private val photoMapper: PhotoMapper
) : FirebaseRepository {

    override suspend fun getAllSiteSamples(): List<SiteSample> {
        return mapper.mapSiteFbDtoMapToSiteSampleModelList(
            firebaseService.getAllSites().entries
                .associate { entry ->
                    entry.key.toObject(SiteFbDto::class.java) to
                            entry.value.map { it.toObject(ConstructionFbDto::class.java) }
                }
        )
    }

    override suspend fun getSitesByName(name: String): List<SiteSample> {
        TODO("Not yet implemented")
    }

    override suspend fun getSiteByIdNoSections(sUuid: String, cUuid: String): Site {
        val siteFbObj = firebaseService.getSiteByIdNoSections(sUuid, cUuid)
        return siteMapper.mapSiteDataFbDtoToSite(
            SiteDataFbDto(
                siteFbDto = siteFbObj.siteFbDto.toObject(SiteFbDto::class.java) ?: throw RuntimeException("No site for mapping"),
                constructions = siteFbObj.constructions.entries.associate { entry ->
                    entry.key to entry.value.map {
                        it.toObject(ConstructionFbDto::class.java) ?: throw RuntimeException("No construction for mapping")
                    }
                },
                measurementConstructions =
                if (siteFbObj.measurementConstructions?.get(cUuid)!!.isNotEmpty()) {
                    siteFbObj.measurementConstructions?.let {
                        it.entries.associate { entry ->
                            entry.key to entry.value.map { documentSnapshot ->
                                documentSnapshot.toObject(MeasurementConstructionFbDto::class.java) ?: throw RuntimeException("No measurement_construction for mapping")
                            }
                        }
                    }
                } else null
            )
        )
    }

    override suspend fun getAllPhotoSamples(): Map<String, String> {
        return photoMapper.mapListResultToPhotoUrlMap(
            firebaseService.getAllPhotoSamples().items
        )
    }

    override suspend fun getSitePhotos(sUuid: String): List<Photo> {
        return photoMapper.mapListResultToPhotoUrlList(
            firebaseService.getSitePhotos(sUuid).items
        )
    }

    override suspend fun getMeasurementConstructionsBySiteId(sUuid: String, cUuid: String): List<MeasurementConstruction> {
        val mcs = siteMapper.mapMeasurementConstructionFbDtoListToMeasurementConstruction(
            firebaseService.getMeasurementConstructionList(sUuid, cUuid).entries.associate { entry ->
                entry.key to entry.value.mapNotNull {
                    it.toObject(MeasurementConstructionFbDto::class.java)
                }
            }
        )

        Log.d("MC", "Date completed: ${mcs.first().completedDate }}")
        return mcs
    }

    override suspend fun getSiteById(uuid: String): Site {
        val siteFbObj = firebaseService.getSiteByIdFull(uuid)
        return siteMapper.mapSiteDataFbDtoToSite(
            SiteDataFbDto(
                siteFbDto = siteFbObj.siteFbDto.toObject(SiteFbDto::class.java) ?: throw RuntimeException("No site for mapping"),
                constructions = siteFbObj.constructions.entries.associate { entry ->
                    entry.key to entry.value.map {
                        it.toObject(ConstructionFbDto::class.java) ?: throw RuntimeException("No construction for mapping")
                    }
                },
                measurementConstructions =
                if (siteFbObj.measurementConstructions!!.isNotEmpty()) {
                    siteFbObj.measurementConstructions!!.entries.associate { entry ->
                        entry.key to entry.value.map { documentSnapshot ->
                            documentSnapshot.toObject(MeasurementConstructionFbDto::class.java) ?: throw RuntimeException("No measurement_construction for mapping")
                        }
                    }

                } else null,
                sections =
                    if (siteFbObj.sections!!.isNotEmpty()) {
                        Log.d("fb", "repository: Mapping sections ${siteFbObj.sections}")
                        siteFbObj.sections!!.entries.associate { entry ->
                            entry.key to entry.value.map {documentSnapshot ->
                                documentSnapshot.toObject(SectionFbDto::class.java) ?: throw RuntimeException("No section for mapping")
                            }
                        }
                    } else null,
                groups =
                    if (siteFbObj.groups!!.isNotEmpty()) {
                        siteFbObj.groups!!.entries.associate { entry ->
                            entry.key to entry.value.map { documentSnapshot ->
                                documentSnapshot.toObject(GroupFbDto::class.java) ?: throw RuntimeException("No group for mapping")
                            }
                        }
                    } else null,
                measurements =
                    if (siteFbObj.measurements!!.isNotEmpty()) {
                        siteFbObj.measurements!!.entries.associate { entry ->
                            entry.key to entry.value.map { documentSnapshot ->
                                documentSnapshot.toObject(MeasurementFbDto::class.java) ?: throw RuntimeException("No group for mapping")
                            }
                        }
                    } else null,
                results = if (siteFbObj.results!!.isNotEmpty()) {
                    siteFbObj.results!!.entries.associate { entry ->
                        entry.key to entry.value.map { documentSnapshot ->
                            documentSnapshot.toObject(ResultFbDto::class.java) ?: throw RuntimeException("No group for mapping")
                        }
                    }
                } else null,
            )
        )
    }
}
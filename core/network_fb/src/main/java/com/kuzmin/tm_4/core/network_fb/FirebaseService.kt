package com.kuzmin.tm_4.core.network_fb

import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.storage.ListResult
import com.google.firebase.storage.StorageReference
import com.kuzmin.tm_4.core.network_fb.model.site.SiteDataFbDtoObj
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseService @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val storageRef: StorageReference,
) {

    suspend fun getAllSites(): Map<DocumentSnapshot, List<DocumentSnapshot>> {
        val siteMap = mutableMapOf<DocumentSnapshot, List<DocumentSnapshot>>()

        //by GroupID. Where Group uuid == groupUuid
        firestore.collection(SITES_COLLECTION).get().await()
            .documents.forEach {
                siteMap[it] = it.reference.collection(CONSTRUCTIONS_COLLECTION)
                    .whereEqualTo("status", "actual")
                    .get()
                    .await().documents
            }
        Log.d("getAll", "SiteMap size: ${siteMap.size}")
        return siteMap
    }

    suspend fun getSiteByIdOnly(siteUuid: String): DocumentSnapshot {
        return firestore.collection(SITES_COLLECTION).document(siteUuid).get().await()
    }

    suspend fun getSiteByIdNoSections(siteUuid: String, cUuid: String): SiteDataFbDtoObj { //Site with actual construction, actual measurement construction

        val siteRef = firestore.collection(SITES_COLLECTION).document(siteUuid)

        val constructionRef = siteRef.collection(CONSTRUCTIONS_COLLECTION).document(cUuid)

        //val measurementConstructions = mutableMapOf<String, List<DocumentSnapshot>>()
            //measurementConstructions[constructionRef.id] =
        val measurementConstruction =
                constructionRef.collection(MEASUREMENT_CONSTRUCTIONS_COLLECTION)
                    .orderBy("completed_date", Query.Direction.DESCENDING)
                    .get()
                    .await().documents.first()

        val construction = constructionRef.get().await()
        val site = siteRef.get().await()

        return SiteDataFbDtoObj(
            siteFbDto = site,
            constructions = mapOf(siteUuid to listOf(construction)),
            measurementConstructions = mapOf(cUuid to listOf(measurementConstruction))
        )
    }

    suspend fun getSiteByIdFull(siteUuid: String): SiteDataFbDtoObj {

        val siteRef = firestore.collection(SITES_COLLECTION).document(siteUuid)

        val constructions = siteRef.collection(CONSTRUCTIONS_COLLECTION).get().await().documents
        val site = siteRef.get().await()

        val sectionsByConstructionUuid = mutableMapOf<String, List<DocumentSnapshot>>()  // Sections Map<construction_UUID, List<Sections of this construction>
        for (i in constructions.indices) {
            val sections = constructions[i].reference.collection(SECTIONS_COLLECTION)
                .orderBy("number").get().await().documents

            sectionsByConstructionUuid[constructions[i].id] = sections
        }

        Log.d("fb", "Sections map: ${sectionsByConstructionUuid}")

        val measurementConstructionsByConstructionUuid = mutableMapOf<String, List<DocumentSnapshot>>()//MeasurementConstructions Map<Construction UUID, List<Measurement_constructions of this construction>
        for (i in constructions.indices) {
            val measurementConstructions = constructions[i].reference.collection(
                MEASUREMENT_CONSTRUCTIONS_COLLECTION
            )
                .orderBy("completed_date", Query.Direction.DESCENDING)
                .get().await().documents

            measurementConstructionsByConstructionUuid[constructions[i].id] = measurementConstructions
            Log.d("fb", "Measurement constructions: ${measurementConstructions.size}, ${measurementConstructions.first().id}")
        }

        val groupsByMeasurementConstruction = mutableMapOf<String, List<DocumentSnapshot>>() // Measurement groups Map<Measurement construction UUID, List<Groups of this measurement construction>
        for ((k,v) in measurementConstructionsByConstructionUuid) {
            v.forEach {
                val groups = it.reference.collection(GROUPS_COLLECTION)
                    .orderBy("group_num")
                    .get().await().documents
                groupsByMeasurementConstruction[it.id] = groups
            }
        }

        val measurementsByGroups = mutableMapOf<String, List<DocumentSnapshot>>()
        val resultsByGroups = mutableMapOf<String, List<DocumentSnapshot>>()
        for ((k,v) in groupsByMeasurementConstruction) {
            v.forEach {
                val measurementsGet = it.reference.collection(MEASUREMENTS_COLLECTION)
                    .orderBy("level")
                    .get()
                val resultsGet = it.reference.collection(RESULTS_COLLECTION)
                    .orderBy("level")
                    .get()

                measurementsByGroups[it.id] = measurementsGet.await().documents
                resultsByGroups[it.id] = resultsGet.await().documents
            }
        }
        return SiteDataFbDtoObj(
            siteFbDto = site,
            constructions = mapOf(siteUuid to constructions),
            measurementConstructions = measurementConstructionsByConstructionUuid,
            sections = sectionsByConstructionUuid,
            groups = groupsByMeasurementConstruction,
            measurements = measurementsByGroups,
            results = resultsByGroups
        )
    }

    suspend fun getAllPhotoSamples(): ListResult {
        return storageRef.child("samples").listAll().await()
    }

    suspend fun getSitePhotos(sUuid: String): ListResult {
        return storageRef.child("images").child(sUuid).listAll().await()
    }

    suspend fun getMeasurementConstructionList(sUuid: String, cUuid: String): Map<String, List<DocumentSnapshot>> {

        return mapOf(cUuid to firestore.collection(SITES_COLLECTION).document(sUuid)
            .collection(CONSTRUCTIONS_COLLECTION).document(cUuid)
            .collection(MEASUREMENT_CONSTRUCTIONS_COLLECTION)
            .get().await().documents)
    }

    companion object {
        private const val SITES_COLLECTION = "sites"
        private const val CONSTRUCTIONS_COLLECTION = "site_constructions"
        private const val MEASUREMENT_CONSTRUCTIONS_COLLECTION = "measurement_constructions"
        private const val SECTIONS_COLLECTION = "sections"
        private const val GROUPS_COLLECTION = "groups"
        private const val MEASUREMENTS_COLLECTION = "measurements"
        private const val RESULTS_COLLECTION = "results"



        private const val SAMPLES_FOLDER = "samples"
        private const val IMAGES_FOLDER = "images"
    }
}
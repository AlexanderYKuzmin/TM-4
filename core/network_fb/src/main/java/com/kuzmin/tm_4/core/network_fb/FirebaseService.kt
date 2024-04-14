package com.kuzmin.tm_4.core.network_fb

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.ListResult
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.tasks.asDeferred
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseService @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val storageRef: StorageReference,
) {

    suspend fun getAllSites(): Map<DocumentSnapshot, List<DocumentSnapshot>> {
        val siteMap = mutableMapOf<DocumentSnapshot, List<DocumentSnapshot>>()

        firestore.collection("sites").get().await()
            .documents.forEach {
                siteMap[it] = it.reference.collection("site_constructions")
                    .whereEqualTo("status", "actual")
                    .get()
                    .await()
                    .documents
            }

            /*.result*/
        Log.d("getAll", "SiteMap size: ${siteMap.size}")
        return siteMap


            /*.addOnSuccessListener { sitesQuerySnapshot ->
                sitesQuerySnapshot.documents.forEach { siteDocSnapshot ->
                    Log.d("getAll", "siteDocSnapshot: ${siteDocSnapshot.data}")

                    siteDocSnapshot.reference.collection("site_constructions")
                        .whereEqualTo("status", "actual")
                        .get()
                        .addOnSuccessListener {
                            siteMap[siteDocSnapshot] = it.documents.first()!!
                            _fbSitesStateFlow.value = siteMap
                        }
                }
            }*/
    }

    suspend fun getAllPhotoSamples(): ListResult {
        return storageRef.child("samples").listAll().await()
    }
}
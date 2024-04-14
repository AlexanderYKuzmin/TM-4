package com.kuzmin.tm_4.data.remote_fb.mapper

import android.util.Log
import com.google.firebase.storage.ListResult
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class PhotoMapper @Inject constructor(
) {
    suspend fun mapListResultToPhotoUrlMap(photoItems: List<StorageReference>): Map<String, String> {
        val photoMap = mutableMapOf<String, String>()

        photoItems.forEach { ref ->
            photoMap[ref.name] = ref.downloadUrl.await().toString()
        }

        Log.d("getAll", "PhotoMap: $photoMap")

        return photoMap
    }
}
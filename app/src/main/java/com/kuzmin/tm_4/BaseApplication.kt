package com.kuzmin.tm_4

import android.app.Application
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.kuzmin.tm_4.common.firebase_resource.FirebaseContainer
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication : Application() {
    override fun onCreate() {
        FirebaseContainer.firestore = Firebase.firestore
        FirebaseContainer.fireStorage = Firebase.storage
        super.onCreate()
    }

}
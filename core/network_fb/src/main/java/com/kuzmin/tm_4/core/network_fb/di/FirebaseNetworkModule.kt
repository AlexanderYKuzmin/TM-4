package com.kuzmin.tm_4.core.network_fb.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import com.kuzmin.tm_4.common.firebase_resource.FirebaseContainer
import com.kuzmin.tm_4.core.network_fb.FirebaseService
import com.kuzmin.tm_4.core.network_fb.fb_service.FirebaseLoginService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class FirebaseNetworkModule {
    @Provides
    fun provideFirestore() = FirebaseContainer.firestore

    @Provides
    fun provideFirebaseStorageReference() = FirebaseContainer.fireStorage.reference

    @Provides
    fun provideFirebaseAuth() = FirebaseContainer.fireAuth

    @Provides
    fun provideFirebaseService(
        fireDb: FirebaseFirestore,
        storageReference: StorageReference
    ): FirebaseService {
        return FirebaseService(fireDb, storageReference)
    }

    @Provides
    fun provideFirebaseLoginService(
        firestore: FirebaseFirestore,
        fireAuth: FirebaseAuth
    ): FirebaseLoginService {
        return FirebaseLoginService(firestore, fireAuth)
    }
}
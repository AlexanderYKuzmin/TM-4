package com.kuzmin.tm_4.core.network_fb.di

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import com.kuzmin.tm_4.common.firebase_resource.FirebaseContainer
import com.kuzmin.tm_4.core.network_fb.FirebaseService
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
    fun provideFirebaseService(
        fireDb: FirebaseFirestore,
        storageReference: StorageReference
    ): FirebaseService {
        return FirebaseService(fireDb, storageReference)
    }
}
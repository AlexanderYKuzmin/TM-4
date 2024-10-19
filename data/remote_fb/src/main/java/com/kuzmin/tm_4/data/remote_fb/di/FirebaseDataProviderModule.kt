package com.kuzmin.tm_4.data.remote_fb.di

import com.kuzmin.tm_4.data.remote_fb.repo.AuthFirebaseRepositoryImpl
import com.kuzmin.tm_4.data.remote_fb.repo.FirebaseRepositoryImpl
import com.kuzmin.tm_4.feature.api.api.AuthFirebaseRepository
import com.kuzmin.tm_4.feature.api.api.FirebaseRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface FirebaseDataProviderModule {

    @Binds
    fun bindFirebaseRepository(firebaseRepositoryImpl: FirebaseRepositoryImpl): FirebaseRepository

    @Binds
    fun bindAuthFirebaseRepository(authFirebaseRepositoryImpl: AuthFirebaseRepositoryImpl): AuthFirebaseRepository
}
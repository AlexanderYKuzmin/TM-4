package com.kuzmin.tm_4.data.remote.di

import com.kuzmin.tm_4.core.network.ApiService
import com.kuzmin.tm_4.data.remote.repository.AuthRepositoryImpl
import com.kuzmin.tm_4.feature.login.data.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RemoteDataModule {
    /*@Binds
    fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository*/

    @Provides
    fun provideAuthRepository(apiService: ApiService): AuthRepository {
        return AuthRepositoryImpl(apiService)
    }
}
package com.kuzmin.tm_4.feature.login.di

import com.kuzmin.tm_4.feature.login.data.PrefManager
import com.kuzmin.tm_4.feature.login.domain.usecases.ReadAuthUserDatastoreUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class LoginModule {
    @Provides
    fun provideCheckAuthUserUseCase(prefManager: PrefManager): ReadAuthUserDatastoreUseCase {
        return ReadAuthUserDatastoreUseCase(prefManager)
    }
}
package com.kuzmin.tm_4.feature.login.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class LoginModule {

    /*@Provides
    fun provideReadAuthUserUseCase(
        prefManager: PrefManager,
    ): ReadAuthUserDatastoreUseCase {
        return ReadAuthUserDatastoreUseCase(prefManager)
    }*/
}
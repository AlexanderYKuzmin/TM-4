package com.kuzmin.tm_4.feature.login.di

import com.kuzmin.tm_4.feature.login.api.PrefManager
import com.kuzmin.tm_4.feature.login.domain.AuthManager
import com.kuzmin.tm_4.feature.login.domain.usecases.GetAuthUserRemoteUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LoginModule {

    /*@Provides
    fun provideReadAuthUserUseCase(
        prefManager: PrefManager,
    ): ReadAuthUserDatastoreUseCase {
        return ReadAuthUserDatastoreUseCase(prefManager)
    }*/

    @Singleton
    @Provides
    fun provideAuthManager(
        prefManager: PrefManager,
        authorizeUseCase: GetAuthUserRemoteUseCase,
    ): AuthManager {
        return AuthManager(
                prefManager,
                authorizeUseCase,
            )
    }
}
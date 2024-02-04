package com.kuzmin.tm_4.feature.login.di

import com.kuzmin.tm_4.feature.login.domain.AuthManager
import com.kuzmin.tm_4.feature.login.domain.usecases.GetAuthUserRemoteUseCase
import com.kuzmin.tm_4.feature.login.api.PrefManager
import com.kuzmin.tm_4.feature.login.domain.usecases.ReadAuthUserDatastoreUseCase
import com.kuzmin.tm_4.feature.login.domain.usecases.WriteAuthUserDatastoreUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LoginModule {

    @Provides
    fun provideReadAuthUserUseCase(
        prefManager: PrefManager,
    ): ReadAuthUserDatastoreUseCase {
        return ReadAuthUserDatastoreUseCase(prefManager)
    }

    @Singleton
    @Provides
    fun provideAuthManager(
        readAuthUserDatastoreUseCase: ReadAuthUserDatastoreUseCase,
        writeAuthUserDatastoreUseCase: WriteAuthUserDatastoreUseCase,
        getAuthUserRemoteUseCase: GetAuthUserRemoteUseCase,
    ): AuthManager {
        return AuthManager(
                readAuthUserDatastoreUseCase,
                writeAuthUserDatastoreUseCase,
                getAuthUserRemoteUseCase,
            )
    }
}
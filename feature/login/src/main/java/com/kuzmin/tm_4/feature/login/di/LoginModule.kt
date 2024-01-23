package com.kuzmin.tm_4.feature.login.di

import com.kuzmin.tm_4.feature.login.data.PrefManager
import com.kuzmin.tm_4.feature.login.domain.model.AuthUser
import com.kuzmin.tm_4.feature.login.domain.model.AuthUserState
import com.kuzmin.tm_4.feature.login.domain.usecases.CheckUserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class LoginModule {
/*    @Provides
    fun provideGetAuthUserUseCase(authRepository: AuthRepository): GetAuthUserUseCase {
        return GetAuthUserUseCase(authRepository)
    }*/

    @Provides
    fun provideCheckAuthUserUseCase(prefManager: PrefManager): CheckUserUseCase {
        return CheckUserUseCase(prefManager)
    }
}
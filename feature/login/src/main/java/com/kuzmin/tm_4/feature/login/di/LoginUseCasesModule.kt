package com.kuzmin.tm_4.feature.login.di

import com.kuzmin.tm_4.feature.login.data.AuthRepository
import com.kuzmin.tm_4.feature.login.domain.usecases.GetAuthUserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class LoginUseCasesModule {
/*    @Provides
    fun provideGetAuthUserUseCase(authRepository: AuthRepository): GetAuthUserUseCase {
        return GetAuthUserUseCase(authRepository)
    }*/
}
package com.kuzmin.tm_4.data.remote.di

import com.kuzmin.tm_4.core.network.UserApiService
import com.kuzmin.tm_4.data.remote.mapper.SitesDtoToModelMapper
import com.kuzmin.tm_4.data.remote.mapper.SitesSamplesDtoToModelMapper
import com.kuzmin.tm_4.data.remote.repository.AuthRepositoryImpl
import com.kuzmin.tm_4.feature.login.data.AuthRepository
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
    fun provideAuthRepository(userApiService: UserApiService): AuthRepository {
        return AuthRepositoryImpl(userApiService)
    }

    @Provides
    fun provideSitesSamplesDtoToModelMapper(): SitesSamplesDtoToModelMapper {
        return SitesSamplesDtoToModelMapper()
    }

    @Provides
    fun provideSitesDtoToModelMapper(): SitesDtoToModelMapper {
        return SitesDtoToModelMapper()
    }
}
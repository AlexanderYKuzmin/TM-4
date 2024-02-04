package com.kuzmin.tm_4.data.remote.di

import com.kuzmin.tm_4.data.remote.mapper.SitesDtoToModelMapper
import com.kuzmin.tm_4.data.remote.mapper.SitesSamplesDtoToModelMapper
import com.kuzmin.tm_4.data.remote.repository.AuthRepositoryImpl
import com.kuzmin.tm_4.data.remote.repository.RemoteSitesRepositoryImpl
import com.kuzmin.tm_4.feature.login.api.AuthRepository
import com.kuzmin.tm_4.feature.sites.RemoteSitesRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RemoteDataModule {
   /* @Binds
    fun bindAuthTokenProvider(authTokenProviderImpl: AuthTokenProviderImpl): AuthTokenProvider*/

    @Binds
    fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    fun bindRemoteSitesRepository(remoteSitesRepositoryImpl: RemoteSitesRepositoryImpl): RemoteSitesRepository

    companion object {
        @Provides
        fun provideSitesSamplesDtoToModelMapper(): SitesSamplesDtoToModelMapper {
            return SitesSamplesDtoToModelMapper()
        }

        @Provides
        fun provideSitesDtoToModelMapper(): SitesDtoToModelMapper {
            return SitesDtoToModelMapper()
        }
    }
}
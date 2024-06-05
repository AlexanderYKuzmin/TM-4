package com.kuzmin.tm_4.data.local.di

import android.content.Context
import com.kuzmin.tm_4.data.local.datastore.PrefManagerImpl
import com.kuzmin.tm_4.data.local.datastore.SearchFilterPrefManagerImpl
import com.kuzmin.tm_4.data.local.datastore.SitePrefManagerImpl
import com.kuzmin.tm_4.data.local.repo.LocalRepositoryImpl
import com.kuzmin.tm_4.feature.api.api.LocalRepository
import com.kuzmin.tm_4.feature.api.api.SearchFilterPrefManager
import com.kuzmin.tm_4.feature.api.api.SitePrefManager
import com.kuzmin.tm_4.feature.login.api.PrefManager
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface LocalDataProviderModule {

    @Binds
    fun bindLocalRepository(localRepositoryImpl: LocalRepositoryImpl): LocalRepository

    companion object {
        @Provides
        fun providePrefManager(@ApplicationContext appContext: Context): PrefManager {
            return PrefManagerImpl(appContext)
        }

        @Provides
        fun provideSitePrefManager(@ApplicationContext appContext: Context): SitePrefManager {
            return SitePrefManagerImpl(appContext)
        }

        @Provides
        fun provideSearchFilterPrefManager(@ApplicationContext appContext: Context): SearchFilterPrefManager {
            return SearchFilterPrefManagerImpl(appContext)
        }
    }
}
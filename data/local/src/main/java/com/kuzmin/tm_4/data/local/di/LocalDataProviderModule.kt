package com.kuzmin.tm_4.data.local.di

import android.content.Context
import com.kuzmin.tm_4.data.local.datastore.PrefManagerImpl
import com.kuzmin.tm_4.feature.login.data.PrefManager
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class LocalDataProviderModule {

    @Provides
    fun providePrefManager(@ApplicationContext appContext: Context): PrefManager {
        return PrefManagerImpl(appContext)
    }
    /*@Binds
    fun bindPrefManager(prefManagerImpl: PrefManagerImpl): PrefManager*/
}
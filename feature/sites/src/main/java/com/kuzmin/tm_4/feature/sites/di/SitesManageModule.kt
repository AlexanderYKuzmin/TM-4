package com.kuzmin.tm_4.feature.sites.di

import androidx.lifecycle.MutableLiveData
import com.kuzmin.tm_4.feature.sites.domain.model.SearchQuerySharedContainer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SitesManageModule {

    @Provides
    @Singleton
    fun provideSearchQuerySharedContainer(searchQuery: MutableLiveData<String>): SearchQuerySharedContainer {
        return SearchQuerySharedContainer(searchQuery)
    }

    @Provides
    @Singleton
    fun provideSearchQuery(): MutableLiveData<String> {
        return MutableLiveData()
    }
}
package com.kuzmin.tm_4.feature.report.di

import com.kuzmin.tm_4.feature.report.domain.model.ChartDataBuilder
import com.kuzmin.tm_4.feature.report.domain.model.ChartDataBuilderXOY
import com.kuzmin.tm_4.feature.report.domain.model.ChartDataBuilderXYProfile
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
class ChartModule {

    @Provides
    @Named("profile")
    fun provideChartBuilderXYProfile(): ChartDataBuilderXYProfile {
        return ChartDataBuilderXYProfile()
    }

    @Provides
    fun provideChartBuilderXOY(): ChartDataBuilderXOY {
        return ChartDataBuilderXOY()
    }
}
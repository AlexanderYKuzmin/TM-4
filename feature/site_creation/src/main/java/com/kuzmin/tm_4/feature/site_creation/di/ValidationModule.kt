package com.kuzmin.tm_4.feature.site_creation.di

import com.kuzmin.tm_4.feature.site_creation.domain.validators.ConstructionStructureValidator
import com.kuzmin.tm_4.feature.site_creation.domain.validators.ConstructionValidator
import com.kuzmin.tm_4.feature.site_creation.domain.validators.ErrorContainerConstruction
import com.kuzmin.tm_4.feature.site_creation.domain.validators.SiteValidator
import com.kuzmin.tm_4.feature.site_creation.domain.validators_api.ErrorContainer
import com.kuzmin.tm_4.feature.site_creation.domain.validators.ErrorContainerSite
import com.kuzmin.tm_4.feature.site_creation.domain.validators_api.StructureValidator
import com.kuzmin.tm_4.feature.site_creation.domain.validators_api.Validator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface ValidationModule {

    @Binds
    @Named("Site")
    fun bindOnSiteValidator(siteValidator: SiteValidator): Validator

    @Binds
    @Named("Construction")
    fun bindOnConstructionValidator(constructionValidator: ConstructionValidator): Validator

    @Binds
    fun bindStructureValidator(constructionStructureValidator: ConstructionStructureValidator): StructureValidator

    @Binds
    @Singleton
    @Named("Site")
    fun bindErrorContainerSite(errorContainer: ErrorContainerSite): ErrorContainer

    @Binds
    @Singleton
    @Named("Construction")
    fun bindErrorContainerConstruction(errorContainer: ErrorContainerConstruction): ErrorContainer
}
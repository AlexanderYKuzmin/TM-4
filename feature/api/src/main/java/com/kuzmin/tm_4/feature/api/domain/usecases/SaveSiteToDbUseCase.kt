package com.kuzmin.tm_4.feature.api.domain.usecases

import android.util.Log
import com.kuzmin.tm_4.common.util.CommonConstants
import com.kuzmin.tm_4.feature.api.api.LocalRepository
import com.kuzmin.tm_4.feature.api.domain.model.site_related_model.site.Site
import javax.inject.Inject

class SaveSiteToDbUseCase @Inject constructor(
    private val localRepository: LocalRepository
) {
    suspend operator fun invoke(site: Site, durability: String = CommonConstants.CONST) =
        localRepository.addSiteToDb(site, durability)
}
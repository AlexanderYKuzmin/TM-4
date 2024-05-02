package com.kuzmin.tm_4.feature.measurements.domain.usecases

import com.kuzmin.tm_4.common.util.CommonConstants
import com.kuzmin.tm_4.feature.api.api.FirebaseRepository
import com.kuzmin.tm_4.feature.api.api.LocalRepository
import com.kuzmin.tm_4.feature.api.model.site.Site
import javax.inject.Inject

class SaveSiteToDbUseCase @Inject constructor(
    private val localRepository: LocalRepository
) {
    suspend operator fun invoke(site: Site, durability: String = CommonConstants.CONST) =
        localRepository.addSiteToDb(site, durability)
}
package com.kuzmin.tm_4.feature.sites.domain.usecases

import com.kuzmin.tm_4.feature.sites.RemoteSitesRepository
import javax.inject.Inject

class GetSitesByNameUseCase @Inject constructor(
    private val remoteSitesRepository: RemoteSitesRepository
) {
    suspend operator fun invoke(name: String) = remoteSitesRepository.getSitesByName(name)
}
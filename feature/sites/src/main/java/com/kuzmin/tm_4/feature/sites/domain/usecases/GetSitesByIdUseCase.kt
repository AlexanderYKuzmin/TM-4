package com.kuzmin.tm_4.feature.sites.domain.usecases

import com.kuzmin.tm_4.feature.sites.RemoteSitesRepository
import com.kuzmin.tm_4.feature.sites.domain.model.sites.Site
import javax.inject.Inject

class GetSitesByIdUseCase @Inject constructor(
    private val remoteSitesRepository: RemoteSitesRepository
) {

    suspend operator fun invoke(ids: List<Long>): List<Site> {
        return remoteSitesRepository.getSitesById(ids)
    }
}
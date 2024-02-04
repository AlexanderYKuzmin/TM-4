package com.kuzmin.tm_4.feature.sites.domain.usecases

import com.kuzmin.tm_4.feature.sites.RemoteSitesRepository
import javax.inject.Inject

class GetSitesByIdUseCase @Inject constructor(
    private val remoteSitesRepository: RemoteSitesRepository
) {
}
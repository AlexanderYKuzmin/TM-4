package com.kuzmin.tm_4.feature.sites.domain.usecases

import com.kuzmin.tm_4.feature.sites.RemoteSitesRepository
import javax.inject.Inject

class GetAllSitesUseCase @Inject constructor(
    private val remoteSitesRepository: RemoteSitesRepository
) {
    suspend operator fun invoke() = remoteSitesRepository.getAllSites()
}
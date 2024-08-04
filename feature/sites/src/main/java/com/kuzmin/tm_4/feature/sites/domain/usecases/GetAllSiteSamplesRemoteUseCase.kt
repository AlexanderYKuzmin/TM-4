package com.kuzmin.tm_4.feature.sites.domain.usecases

import com.kuzmin.tm_4.feature.api.api.FirebaseRepository
import javax.inject.Inject

class GetAllSiteSamplesRemoteUseCase @Inject constructor(
    //private val remoteSitesRepository: RemoteSitesRepository
    private val firebaseRepository: FirebaseRepository
) {
    //suspend operator fun invoke() = remoteSitesRepository.getAllSites()

    suspend operator fun invoke() = firebaseRepository.getAllSiteSamples()
}
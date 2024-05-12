package com.kuzmin.tm_4.feature.sites.domain.usecases

import com.kuzmin.tm_4.feature.api.api.FirebaseRepository
import com.kuzmin.tm_4.feature.api.domain.model.sample.SiteSample
import javax.inject.Inject

class GetSitesByNameUseCase @Inject constructor(
    //private val remoteSitesRepository: RemoteSitesRepository
    private val firebaseRepository: FirebaseRepository
) {
    suspend operator fun invoke(name: String): List<SiteSample> {
        return  firebaseRepository.getSitesByName(name)
    }
}
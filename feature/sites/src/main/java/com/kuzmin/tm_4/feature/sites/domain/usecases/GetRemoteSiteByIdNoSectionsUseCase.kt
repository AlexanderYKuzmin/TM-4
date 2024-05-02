package com.kuzmin.tm_4.feature.sites.domain.usecases

import com.kuzmin.tm_4.feature.api.api.FirebaseRepository
import javax.inject.Inject

class GetRemoteSiteByIdNoSectionsUseCase @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) {
    suspend operator fun invoke(sUuid: String, cUuid: String) =
        firebaseRepository.getSiteByIdNoSections(sUuid, cUuid)
}
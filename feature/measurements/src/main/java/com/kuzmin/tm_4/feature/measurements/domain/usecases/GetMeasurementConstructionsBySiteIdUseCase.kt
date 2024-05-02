package com.kuzmin.tm_4.feature.measurements.domain.usecases

import com.kuzmin.tm_4.feature.api.api.FirebaseRepository
import javax.inject.Inject

class GetMeasurementConstructionsBySiteIdUseCase @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) {
    suspend operator fun invoke(siteUuid: String, constructionUuid: String) =
        firebaseRepository.getMeasurementConstructionsBySiteId(siteUuid, constructionUuid)
}
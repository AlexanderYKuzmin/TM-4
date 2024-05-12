package com.kuzmin.tm_4.feature.api.domain.usecases

import com.kuzmin.tm_4.feature.api.api.LocalRepository
import javax.inject.Inject

class GetMeasurementConstructionFromDbUseCase @Inject constructor(
    private val localRepository: LocalRepository
) {
    suspend operator fun invoke(mcUuid: String) =
        localRepository.getMc(mcUuid)
}
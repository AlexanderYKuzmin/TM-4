package com.kuzmin.tm_4.feature.sites.domain.usecases

import com.kuzmin.tm_4.feature.api.api.LocalRepository
import javax.inject.Inject

class GetAllPhotoSamplesLocalUseCase @Inject constructor(
    private val localRepository: LocalRepository
){
    suspend operator fun invoke() = localRepository.getAllPhotoSamples()
}
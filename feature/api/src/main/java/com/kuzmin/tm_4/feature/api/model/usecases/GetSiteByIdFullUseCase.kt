package com.kuzmin.tm_4.feature.api.model.usecases

import com.kuzmin.tm_4.feature.api.api.FirebaseRepository
import javax.inject.Inject

class GetSiteByIdFullUseCase @Inject constructor(
    private val firebaseRepository: FirebaseRepository
){
    suspend operator fun invoke(uuid: String) = firebaseRepository.getSiteById(uuid)
}
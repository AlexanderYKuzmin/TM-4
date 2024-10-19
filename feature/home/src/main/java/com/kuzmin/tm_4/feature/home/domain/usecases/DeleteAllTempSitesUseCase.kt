package com.kuzmin.tm_4.feature.home.domain.usecases

import com.kuzmin.tm_4.feature.api.api.LocalRepository
import javax.inject.Inject

class DeleteAllTempSitesUseCase @Inject constructor(
    private val repository: LocalRepository
) {

    suspend operator fun invoke() {
        repository.deleteAllTempSites()
    }
}
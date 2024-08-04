package com.kuzmin.tm_4.feature.sites.domain.usecases

import android.util.Log
import com.kuzmin.tm_4.feature.api.api.LocalRepository
import com.kuzmin.tm_4.feature.api.domain.model.sample.SiteSample
import javax.inject.Inject

class GetAllSiteSamplesLocalUseCase @Inject constructor(
    private val localRepository: LocalRepository
) {
    suspend operator fun invoke(): List<SiteSample> {
        val list = localRepository.getAllSiteSamples()
        Log.d("Get all", "UseCase. list of samples: ${list.size} ")
        return list
    }
}
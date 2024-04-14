package com.kuzmin.tm_4.feature.sites.domain.usecases

import com.kuzmin.tm_4.feature.api.api.FirebaseRepository
import com.kuzmin.tm_4.feature.api.model.site.RemoteSitesRepository
import com.kuzmin.tm_4.feature.api.model.site.Site
import javax.inject.Inject

class GetSitesByIdUseCase @Inject constructor(
    //private val remoteSitesRepository: RemoteSitesRepository
    private val firebaseRepository: FirebaseRepository
) {

    suspend operator fun invoke(ids: List<Long>): List<Site> {
        return emptyList()
    }
}
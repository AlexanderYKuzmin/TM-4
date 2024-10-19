package com.kuzmin.tm_4.feature.api.domain.usecases

import com.kuzmin.tm_4.feature.api.api.LocalRepository
import com.kuzmin.tm_4.feature.api.domain.model.site_related_model.site.Construction
import javax.inject.Inject

class SaveConstructionToDbUseCase @Inject constructor(
    private val localRepository: LocalRepository
) {
    //suspend operator fun invoke(construction: Construction) =

}
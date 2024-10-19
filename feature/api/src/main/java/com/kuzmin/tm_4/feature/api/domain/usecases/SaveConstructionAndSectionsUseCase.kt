package com.kuzmin.tm_4.feature.api.domain.usecases

import com.kuzmin.tm_4.feature.api.api.LocalRepository
import com.kuzmin.tm_4.feature.api.domain.model.site_related_model.site.model_complex.ConstructionAndSections
import javax.inject.Inject

class SaveConstructionAndSectionsToDbUseCase @Inject constructor(
    private val localRepository: LocalRepository
) {
    suspend operator fun invoke(constructionAndSections: ConstructionAndSections) =
        localRepository.addConstructionAndSectionsToDb(constructionAndSections)
}
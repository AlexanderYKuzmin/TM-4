package com.kuzmin.tm_4.feature.site_creation.domain.validators

import com.kuzmin.tm_4.feature.api.domain.model.model_complex.ConstructionAndSections
import com.kuzmin.tm_4.feature.site_creation.domain.validators_api.StructureValidator
import javax.inject.Inject

class ConstructionStructureValidator @Inject constructor() : StructureValidator {
    override fun validateStructure(structure: ConstructionAndSections): Boolean {
        return checkHeightConstructionWithSections(structure)
    }

    private fun checkHeightConstructionWithSections(constructionAndSections: ConstructionAndSections): Boolean {
        return constructionAndSections.construction.height == constructionAndSections.sections.sumOf { it.height }
    }
}
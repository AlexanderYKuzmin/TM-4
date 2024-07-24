package com.kuzmin.tm_4.feature.site_creation.domain.validators

import com.kuzmin.tm_4.feature.api.domain.model.model_complex.ConstructionAndSections
import com.kuzmin.tm_4.feature.api.domain.model.site.Construction
import com.kuzmin.tm_4.feature.api.domain.model.site.Section
import javax.inject.Inject

class ConstructionStructureValidator @Inject constructor() {

    fun validateAll(constructionAndSections: ConstructionAndSections): Boolean {
        return validateConstructionDimensions(constructionAndSections)
    }

    private fun validateConstructionDimensions(constructionAndSections: ConstructionAndSections): Boolean {
        with(constructionAndSections) {
            if (!isControlSumValid(construction, sections)) return false
        }
        return true
    }

    private fun isControlSumValid(construction: Construction, sections: List<Section>): Boolean {
        val height = construction.height
        val sectionsHeightSum = sections.sumOf { it.height }
        return height == sectionsHeightSum
    }
}
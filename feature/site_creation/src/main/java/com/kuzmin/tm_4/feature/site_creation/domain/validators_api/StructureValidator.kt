package com.kuzmin.tm_4.feature.site_creation.domain.validators_api

import com.kuzmin.tm_4.feature.api.domain.model.site_related_model.site.model_complex.ConstructionAndSections

interface StructureValidator {
    fun validateStructure(structure: ConstructionAndSections): Boolean
}
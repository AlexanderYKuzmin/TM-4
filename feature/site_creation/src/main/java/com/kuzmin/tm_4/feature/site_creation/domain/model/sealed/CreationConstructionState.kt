package com.kuzmin.tm_4.feature.site_creation.domain.model.sealed

import com.kuzmin.tm_4.feature.api.domain.model.site_related_model.site.Construction
import com.kuzmin.tm_4.feature.site_creation.domain.model.ErrorInfo

sealed class CreationConstructionState {
    data object Loading : CreationConstructionState()

    class SuccessConstructionDefault(val date: String) : CreationConstructionState()

    class SuccessConstructionBySiteUuid(val construction: Construction) : CreationConstructionState()

    class ConstructionValidationField(val errorInfo: ErrorInfo): CreationConstructionState()

    class ConstructionValidationStatus(val isValid: Boolean): CreationConstructionState()

    data class SuccessConstructionSaved(val constructionUuid: String?) : CreationConstructionState()

    class Error(val throwable: Throwable): CreationConstructionState()
}
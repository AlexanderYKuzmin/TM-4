package com.kuzmin.tm_4.feature.site_creation.domain.model.sealed

import com.kuzmin.tm_4.feature.api.domain.model.site.Site
import com.kuzmin.tm_4.feature.site_creation.domain.model.ErrorInfo

sealed class CreationState {
    data object Loading : CreationState()

    data object SuccessGetDefault : CreationState()

    class SuccessGetByUuid(site: Site) : CreationState()

    data class SuccessSavedToDb(val uuid: String?, val launchFlag: Int) : CreationState()

    data class ValidationField(val errorInfo: ErrorInfo?) : CreationState()

    data class ValidationStatus(val isValid: Boolean) : CreationState()

    class Error(val throwable: Throwable): CreationState()

    data object DefaultState: CreationState()
}
package com.kuzmin.tm_4.feature.site_creation.domain.model.sealed

sealed class ValidatorState {
    data class OnSite(val isSet: Boolean) : ValidatorState()
    data class OnConstruction(val isSet: Boolean) : ValidatorState()
    data class Error(val throwable: Throwable) : ValidatorState()
}
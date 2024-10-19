package com.kuzmin.tm_4.domain.model.sealed

import com.kuzmin.tm_4.common.util.CommonConstants.STORAGE_LOCAL

sealed class AppState {
    data class LoginState(val actionKey: String) : AppState()
    data class FilterState(
        val actionKey: String,
        val isFilterSet: Boolean,
        val storage: Int
    ): AppState()
    data object HomeState : AppState()
    data class SiteListSate(
        val actionKey: String,
        val storage: Int = STORAGE_LOCAL,
        val isFilterSet: Boolean = false,
    ): AppState()
    data class SingleSiteState(val name: String?, val isFragmentActive: Boolean = false): AppState()
    data class SiteCreationState(val sUuid: String?) : AppState()

}
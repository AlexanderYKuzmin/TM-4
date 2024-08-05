package com.kuzmin.tm_4.model.sealed

sealed class AppState {
    data object LoginState : AppState()
    //data class SearchFilterState(val isFilterSet: Boolean, val storage: Int): AppState()
    data class SiteListSate(val storage: Int, val isFilterSet: Boolean): AppState()
    data class SingleSiteState(val name: String?): AppState()
    data class SiteCreationState(val sUuid: String?) : AppState()
    data class ToolbarState(val isLoginCompleted: Boolean, val appTitle: String? = null): AppState()
}
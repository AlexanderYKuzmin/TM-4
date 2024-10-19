package com.kuzmin.tm_4.domain.model

data class ToolbarState(
    val isLoginCompleted: Boolean,
    val appTitle: String? = null,
    val isLogoVisible: Boolean = true,
    val isHomeButtonEnable: Boolean = true
)
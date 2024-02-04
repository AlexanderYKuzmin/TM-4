package com.kuzmin.tm_4.model

data class AppState(
//val isNavBarVisible: Boolean = true,
    //val isAppbarLogoVisible: Boolean = true,
    val mode: ScreenMode = ScreenMode.HOME,
    val title: String,
    val searchQuery: String = "",
)

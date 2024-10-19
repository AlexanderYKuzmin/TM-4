package com.kuzmin.tm_4.feature.api.domain.model.user

data class AuthFbInfo(
    val authUser: AuthUser? = null,
    val throwable: Throwable? = null,
    val message: String? = null
) {
    val hasError = throwable != null
}
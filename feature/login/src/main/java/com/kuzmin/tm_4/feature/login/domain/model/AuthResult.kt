package com.kuzmin.tm_4.feature.login.domain.model

sealed class AuthResult {
    class Success(val authUser: AuthUser): AuthResult()
    class Error(val throwable: Throwable): AuthResult()
}

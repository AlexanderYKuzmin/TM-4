package com.kuzmin.tm_4.feature.login.domain.model

sealed class AuthUserState {
    class Error<Throwable>(throwable: kotlin.Throwable): AuthUserState()
    class Success<AuthUser>(authUser: AuthUser): AuthUserState()
}
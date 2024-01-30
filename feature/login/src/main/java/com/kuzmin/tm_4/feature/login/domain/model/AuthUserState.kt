package com.kuzmin.tm_4.feature.login.domain.model

sealed class AuthUserState {
    class Default<AuthUser>(val authUser: AuthUser): AuthUserState()
    class Error<Throwable>(val throwable:Throwable): AuthUserState()
    class Success<AuthUser>(val authUser: AuthUser): AuthUserState()
}
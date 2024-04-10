package com.kuzmin.tm_4.feature.login.domain.model

sealed class AuthUserResult {
    class Default<AuthUser>(val authUser: AuthUser): AuthUserResult()
    class Error<Throwable>(val throwable:Throwable, val user: User): AuthUserResult()
    class Success<User>(val user: User): AuthUserResult()
}
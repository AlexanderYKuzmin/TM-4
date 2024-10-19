package com.kuzmin.tm_4.feature.login.domain.model

import com.kuzmin.tm_4.feature.api.domain.model.user.AuthUser

sealed class AuthResult {
    class Success(val authUser: AuthUser): AuthResult()
    class Error(val throwable: Throwable): AuthResult()
}

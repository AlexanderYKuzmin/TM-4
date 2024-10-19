package com.kuzmin.tm_4.core.network_fb.model.uer

sealed class AuthFbTaskResult {
    class Success(val userFb: UserFb): AuthFbTaskResult()
    class Error(val throwable: Throwable): AuthFbTaskResult()
}
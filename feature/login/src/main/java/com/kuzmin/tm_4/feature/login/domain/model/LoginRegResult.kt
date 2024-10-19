package com.kuzmin.tm_4.feature.login.domain.model

import com.kuzmin.tm_4.feature.api.domain.model.user.AuthFbInfo
import com.kuzmin.tm_4.feature.api.domain.model.user.AuthUser

sealed class LoginRegResult {
    class LocalUserCheck(val authUser: AuthUser?): LoginRegResult()
    class Error(val throwable:Throwable): LoginRegResult()
    class Success(val authFbInfo: AuthFbInfo): LoginRegResult()
    class Failure(val authFbInfo: AuthFbInfo): LoginRegResult()
}


package com.kuzmin.tm_4.feature.login.domain

import com.kuzmin.tm_4.common.util.CommonConstants
import com.kuzmin.tm_4.feature.login.domain.model.AuthUser
import com.kuzmin.tm_4.feature.login.domain.model.User
import com.kuzmin.tm_4.feature.login.domain.usecases.GetAuthUserRemoteUseCase
import com.kuzmin.tm_4.feature.login.domain.usecases.ReadAuthUserDatastoreUseCase
import com.kuzmin.tm_4.feature.login.domain.usecases.WriteAuthUserDatastoreUseCase
import java.util.Date
import javax.inject.Inject

class AuthManager @Inject constructor(
    private val readAuthUserDatastoreUseCase: ReadAuthUserDatastoreUseCase,
    private val writeAuthUserDatastoreUseCase: WriteAuthUserDatastoreUseCase,
    private val getAuthUserRemoteUseCase: GetAuthUserRemoteUseCase
) {

    suspend fun getAuthUser(user: User): AuthUser { // TODO переделать чтобы рефреш был а не просто вернуть auth
        var auth = readAuthUserDatastoreUseCase()
        if (isAuthUserValid(auth)) return auth

        auth = getAuthUserRemoteUseCase(user)
        if (isAuthUserValid(auth)) {
            writeAuthUserDatastoreUseCase(auth)
            return auth
        }
        throw RuntimeException()
    }

    suspend fun getToken(): String {
        return readAuthUserDatastoreUseCase().authToken
    }

    suspend fun isUserAuthorized(): Boolean {
        return isAuthUserValid(readAuthUserDatastoreUseCase())
    }

    /*suspend fun obtainToken(): String {
        return ""
    }*/

    private fun isAuthUserValid(authUser: AuthUser): Boolean {
        with(this) {
            return if (authUser.token != CommonConstants.NO_TOKEN && authUser.dateToken != CommonConstants.NO_DATE) {
                isTokenValid(authUser.token, authUser.dateToken)
            } else false
        }
    }

    private fun isTokenValid(token: String, tokenDate: Long) : Boolean {
        return if (token.trim().length > 10) {
            val currentTime = Date().time
            val tokenExpirationTime = tokenDate + CommonConstants.TOKEN_LIFE_TIME - CommonConstants.DEVIATION_TOKEN_LIFE_TIME
            currentTime < tokenExpirationTime
        } else false
    }

    /*private fun isPasswordConsistent(): Boolean {
        return password.isPasswordConsistent()
    }

    private fun isNameConsistent(): Boolean {
        return username.isNameConsistent()
    }*/
}
package com.kuzmin.tm_4.feature.login.domain

import android.util.Log
import com.kuzmin.tm_4.common.util.CommonConstants
import com.kuzmin.tm_4.feature.login.api.PrefManager
import com.kuzmin.tm_4.feature.login.domain.model.AuthUser
import com.kuzmin.tm_4.feature.login.domain.model.User
import com.kuzmin.tm_4.feature.login.domain.usecases.GetAuthUserRemoteUseCase
import com.kuzmin.tm_4.feature.login.domain.usecases.ReadAuthUserDatastoreUseCase
import com.kuzmin.tm_4.feature.login.domain.usecases.WriteAuthUserDatastoreUseCase
import java.util.Date
import javax.inject.Inject

class AuthManager @Inject constructor(
    private val prefManager: PrefManager,
    private val authorizeUseCase: GetAuthUserRemoteUseCase
) {

    suspend fun saveAuthUser(authUser: AuthUser) {
        prefManager.writeData(authUser)
    }

    suspend fun getAuthUser(): AuthUser? {
        val auth = prefManager.readData()
        return if (isAuthUserValid(auth)) auth else null
    }

    suspend fun getUser(): User {
        return prefManager.readUserData()
    }

    suspend fun getToken(): String {
        return prefManager.readData().authToken
    }

    suspend fun authorize(user: User): AuthUser {
        val auth = authorizeUseCase(user)
        return if (isAuthUserValid(auth)) {
            prefManager.writeData(auth)
            auth
        } else throw RuntimeException("Authorization attempt error!")
    }

    suspend fun cancelAuthorization() {
        prefManager.clearAuthData()
    }

    suspend fun isUserAuthorized(): Boolean {
        return isAuthUserValid(prefManager.readData())
    }

    private fun isAuthUserValid(authUser: AuthUser): Boolean {
        Log.d("MainActivity", "isAuthUser valid authUser: $authUser")
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
            Log.d("MainActivity", "${currentTime - tokenExpirationTime}")
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
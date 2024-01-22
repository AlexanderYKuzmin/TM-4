package com.kuzmin.tm_4.feature.login.util


import com.kuzmin.tm_4.feature.login.domain.model.AuthUser
import java.util.*

object AuthValidation {
    fun isAuthUserValid(authUser: AuthUser?): Boolean {
        authUser?.let {
            with(it) {
                return if (token != LoginConstants.NO_TOKEN && dateToken != LoginConstants.NO_DATE) {
                    isTokenValid(token, dateToken)
                } else false
            }
        }
        return false
    }

    fun isTokenValid(token: String, tokenDate: Long) : Boolean {
        return if (token.trim().length > 10) {
            val currentTime = Date().time
            val tokenExpirationTime = tokenDate + LoginConstants.TOKEN_LIFE_TIME - LoginConstants.DEVIATION_TOKEN_LIFE_TIME
            currentTime < tokenExpirationTime
        } else false
    }

    fun isPasswordConsistent(password: String): Boolean {
        return password.trim().length > 7
    }

    fun isNameConsistent(name: String): Boolean {
        return name.trim().length > 2
    }
}
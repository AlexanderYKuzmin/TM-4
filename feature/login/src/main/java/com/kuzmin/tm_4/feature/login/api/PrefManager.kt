package com.kuzmin.tm_4.feature.login.api

import com.kuzmin.tm_4.feature.login.domain.model.AuthUser
import com.kuzmin.tm_4.feature.login.domain.model.User

interface PrefManager {
    suspend fun writeData(authUser: AuthUser)

    suspend fun clearAuthData()

    //suspend fun read(): AuthUserPref

    suspend fun readData(): AuthUser

    suspend fun readUserData(): User
}
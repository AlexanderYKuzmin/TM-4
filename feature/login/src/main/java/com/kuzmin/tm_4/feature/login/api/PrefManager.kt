package com.kuzmin.tm_4.feature.login.api

import com.kuzmin.tm_4.feature.api.domain.model.user.AuthUser
import com.kuzmin.tm_4.feature.api.domain.model.user.User

interface PrefManager {
    suspend fun writeData(authUser: AuthUser)

    suspend fun clearAuthData()

    suspend fun readData(): AuthUser

    suspend fun readUserData(): User
}
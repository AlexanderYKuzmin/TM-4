package com.kuzmin.tm_4.feature.login.api

import com.kuzmin.tm_4.feature.login.domain.model.AuthUser

interface PrefManager {
    suspend fun writeData(authUser: AuthUser)

    //suspend fun read(): AuthUserPref

    suspend fun readData(): AuthUser
}
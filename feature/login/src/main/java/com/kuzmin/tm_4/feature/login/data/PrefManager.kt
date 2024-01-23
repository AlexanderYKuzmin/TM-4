package com.kuzmin.tm_4.feature.login.data

import com.kuzmin.tm_4.feature.login.domain.model.AuthUser
import com.kuzmin.tm_4.feature.login.util.LoginConstants

interface PrefManager {
    suspend fun writeData(
        username: String,
        password: String,
        token: String = LoginConstants.NO_TOKEN,
        dateToken: Long = LoginConstants.NO_DATE,
        remoteId: Long = LoginConstants.NO_ID,
        firstName: String,
        lastName: String,
    )

    suspend fun read(): AuthUser
}
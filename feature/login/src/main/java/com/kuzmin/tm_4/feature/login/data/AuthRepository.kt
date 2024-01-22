package com.kuzmin.tm_4.feature.login.data

import com.kuzmin.tm_4.feature.login.domain.model.AuthUser


interface AuthRepository {
    suspend fun authenticate(username: String, password: String): AuthUser
}
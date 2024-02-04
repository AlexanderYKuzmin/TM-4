package com.kuzmin.tm_4.feature.login.api

import com.kuzmin.tm_4.feature.login.domain.model.AuthUser
import com.kuzmin.tm_4.feature.login.domain.model.User


interface AuthRepository {
    suspend fun authenticate(user: User): AuthUser
}
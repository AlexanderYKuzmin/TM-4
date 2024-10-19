package com.kuzmin.tm_4.feature.api.api

import com.kuzmin.tm_4.feature.api.domain.model.user.AuthFbInfo
import com.kuzmin.tm_4.feature.api.domain.model.user.AuthUser
import com.kuzmin.tm_4.feature.api.domain.model.user.User

interface AuthFirebaseRepository {
    suspend fun registerUser(user: User): AuthFbInfo

    suspend fun signIn(authUser: AuthUser): AuthFbInfo

    suspend fun signOut(authUser: AuthUser): AuthFbInfo
}
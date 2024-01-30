package com.kuzmin.tm_4.data.remote.repository

import com.kuzmin.tm_4.core.network.RequestInterceptor
import com.kuzmin.tm_4.core.network.TokenContainer
import com.kuzmin.tm_4.core.network.UserApiService
import com.kuzmin.tm_4.core.network.model.user.UserSignInDto
import com.kuzmin.tm_4.feature.login.data.AuthRepository
import com.kuzmin.tm_4.feature.login.domain.model.AuthUser
import com.kuzmin.tm_4.feature.login.util.LoginConstants.NO_PASSWORD
import java.util.Date
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val userApiService: UserApiService
) : AuthRepository {
    override suspend fun authenticate(username: String, password: String): AuthUser {
        try {
            val user = userApiService.getUser(UserSignInDto(username, password))
            TokenContainer.token = user.token
            return AuthUser(
                username = user.username,
                password = NO_PASSWORD,
                token = user.token,
                dateToken = Date().time,
                remoteId = user.userIdRemote,
                firstName = user.firstName,
                lastName = user.lastName
            )
        } catch (e: Exception) {
            TODO("Not yet implemented")
        }
    }
}
package com.kuzmin.tm_4.data.remote.repository

import com.kuzmin.tm_4.core.network.ApiService
import com.kuzmin.tm_4.core.network.model.UserSignInDto
import com.kuzmin.tm_4.feature.login.data.AuthRepository
import com.kuzmin.tm_4.feature.login.domain.model.AuthUser
import com.kuzmin.tm_4.feature.login.util.LoginConstants.NO_PASSWORD
import java.util.Date
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : AuthRepository {
    override suspend fun authenticate(username: String, password: String): AuthUser {
        val user = apiService.getUser(UserSignInDto(username, password))
        return AuthUser(
            username = user.username,
            password = NO_PASSWORD,
            token = user.token,
            dateToken = Date().time,
            remoteId = user.userIdRemote,
            firstName = user.firstName,
            lastName = user.lastName
        )
    }
}
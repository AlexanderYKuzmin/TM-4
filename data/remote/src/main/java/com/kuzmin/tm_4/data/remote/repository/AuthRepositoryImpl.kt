package com.kuzmin.tm_4.data.remote.repository

import com.kuzmin.tm_4.common.util.CommonConstants.NO_PASSWORD
import com.kuzmin.tm_4.core.network.UserApiService
import com.kuzmin.tm_4.core.network.model.user.UserSignInDto
import com.kuzmin.tm_4.feature.login.api.AuthRepository
import com.kuzmin.tm_4.feature.login.domain.model.AuthUser
import com.kuzmin.tm_4.feature.login.domain.model.User
import java.util.Date
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val userApiService: UserApiService
) : AuthRepository {
    override suspend fun authenticate(user: User): AuthUser {
        try {
            val tempUser = userApiService.getUser(UserSignInDto(user.username, user.password))
            with(tempUser) {
                return AuthUser(
                    username = username,
                    password = NO_PASSWORD,
                    token = token,
                    dateToken = Date().time,
                    remoteId = userIdRemote,
                    firstName = firstName,
                    lastName = lastName
                )
            }
        } catch (e: Exception) {
            TODO("Not yet implemented")
        }
    }
}
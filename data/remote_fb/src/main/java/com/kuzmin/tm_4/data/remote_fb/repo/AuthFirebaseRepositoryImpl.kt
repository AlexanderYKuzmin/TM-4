package com.kuzmin.tm_4.data.remote_fb.repo

import com.kuzmin.tm_4.common.util.messages.LoginMessage.AUTHORIZATION_ERROR
import com.kuzmin.tm_4.core.network_fb.fb_service.FirebaseLoginService
import com.kuzmin.tm_4.core.network_fb.model.uer.AuthFbTaskResult
import com.kuzmin.tm_4.data.remote_fb.mapper.user.UserMapper
import com.kuzmin.tm_4.feature.api.api.AuthFirebaseRepository
import com.kuzmin.tm_4.feature.api.domain.model.user.AuthFbInfo
import com.kuzmin.tm_4.feature.api.domain.model.user.AuthUser
import com.kuzmin.tm_4.feature.api.domain.model.user.User
import javax.inject.Inject

class AuthFirebaseRepositoryImpl @Inject constructor(
    private val userMapper: UserMapper,
    private val firebaseLoginService: FirebaseLoginService
) : AuthFirebaseRepository {

    override suspend fun registerUser(user: User): AuthFbInfo {
        TODO("Not yet implemented")
    }

    override suspend fun signIn(authUser: AuthUser): AuthFbInfo {
        val userFb = userMapper.mapAuthUserToUserFb(authUser)

        return when (val authFbTaskResult = firebaseLoginService.signIn(userFb)) {
            is AuthFbTaskResult.Success -> {
                if (isAuthUserUidConsistent(authFbTaskResult, authUser.uid)) {
                    AuthFbInfo(
                        authUser = authUser
                    )
                } else {
                    throw RuntimeException("Uid is not consistent")
                }
            }
            is AuthFbTaskResult.Error -> {
                AuthFbInfo(
                    authUser = authUser,
                    throwable = authFbTaskResult.throwable,
                    message = AUTHORIZATION_ERROR
                )
            }
            else -> { throw RuntimeException("Unknown authorization result") }
        }
    }

    override suspend fun signOut(authUser: AuthUser): AuthFbInfo {
        TODO("Not yet implemented")
    }

    private fun isAuthUserUidConsistent(authFbTaskResult: AuthFbTaskResult.Success, uid: String): Boolean {
        return authFbTaskResult.userFb.uid == uid
    }
}
package com.kuzmin.tm_4.data.remote_fb.mapper.user

import com.kuzmin.tm_4.core.network_fb.model.uer.UserFb
import com.kuzmin.tm_4.feature.api.domain.model.user.AuthUser
import com.kuzmin.tm_4.feature.api.domain.model.user.User
import javax.inject.Inject

class UserMapper @Inject constructor() {
    fun mapUserToUserFb(user: User): UserFb {
        with(user) {
            if (email.isEmpty() || password.isEmpty()) throw RuntimeException("Email or password is empty")
        }
        return UserFb(
            email = user.email,
            password = user.password,
            isAdmin = user.isAdmin
        )
    }

    fun mapAuthUserToUserFb(authUser: AuthUser): UserFb {
        with(authUser) {
            if (email.isEmpty() || password.isEmpty()) throw RuntimeException("Email or password is empty")
            if (!authUser.isRegistered) throw RuntimeException("User is not registered")
        }

        return UserFb(
            email = authUser.email,
            password = authUser.password,
            isAdmin = authUser.isAdmin
        )
    }
}
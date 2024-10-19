package com.kuzmin.tm_4.feature.login.domain.usecases

import com.kuzmin.tm_4.feature.api.api.AuthFirebaseRepository
import com.kuzmin.tm_4.feature.api.domain.model.user.AuthUser
import com.kuzmin.tm_4.feature.api.domain.model.user.User
import javax.inject.Inject

class AuthorizeUseCase @Inject constructor(
    private val authFirebaseRepository: AuthFirebaseRepository
){
    suspend operator fun invoke(authUser: AuthUser) = authFirebaseRepository.signIn(authUser)
}
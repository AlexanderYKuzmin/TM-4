package com.kuzmin.tm_4.feature.login.domain.usecases

import com.kuzmin.tm_4.feature.login.api.AuthRepository
import com.kuzmin.tm_4.feature.login.domain.model.User
import javax.inject.Inject

class GetAuthUserRemoteUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(user: User) = authRepository.authenticate(user)
}
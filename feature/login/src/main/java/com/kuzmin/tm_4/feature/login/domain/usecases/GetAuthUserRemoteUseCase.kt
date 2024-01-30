package com.kuzmin.tm_4.feature.login.domain.usecases

import com.kuzmin.tm_4.feature.login.data.AuthRepository
import javax.inject.Inject

class GetAuthUserRemoteUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(username: String, password: String) =
        authRepository.authenticate(username, password)
}
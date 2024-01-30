package com.kuzmin.tm_4.feature.login.domain.usecases

import com.kuzmin.tm_4.feature.login.data.PrefManager
import com.kuzmin.tm_4.feature.login.domain.model.AuthUser
import javax.inject.Inject

class WriteAuthUserDatastoreUseCase @Inject constructor(
    private val prefManager: PrefManager
){
    suspend operator fun invoke(authUser: AuthUser) =
        prefManager.writeData(
            authUser.username,
            authUser.password,
            authUser.token,
            authUser.dateToken,
            authUser.remoteId,
            authUser.firstName,
            authUser.lastName
        )
}
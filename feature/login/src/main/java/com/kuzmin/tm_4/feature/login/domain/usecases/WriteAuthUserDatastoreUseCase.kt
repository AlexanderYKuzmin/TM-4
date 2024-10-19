package com.kuzmin.tm_4.feature.login.domain.usecases

import com.kuzmin.tm_4.feature.login.api.PrefManager
import com.kuzmin.tm_4.feature.api.domain.model.user.AuthUser
import javax.inject.Inject

class WriteAuthUserDatastoreUseCase @Inject constructor(
    private val prefManager: PrefManager
){
    suspend operator fun invoke(authUser: AuthUser) =
        prefManager.writeData(authUser)
}
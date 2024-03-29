package com.kuzmin.tm_4.feature.login.domain.usecases

import com.kuzmin.tm_4.feature.login.api.PrefManager
import javax.inject.Inject

class ReadAuthUserDatastoreUseCase @Inject constructor(
    private val prefManager: PrefManager,
) {
    suspend operator fun invoke() = prefManager.readData()
}
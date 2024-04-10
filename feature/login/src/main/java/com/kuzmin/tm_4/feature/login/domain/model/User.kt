package com.kuzmin.tm_4.feature.login.domain.model

import com.kuzmin.tm_4.common.util.CommonConstants.NO_PASSWORD
import com.kuzmin.tm_4.common.util.CommonConstants.NO_USERNAME

data class User(
    val username: String = NO_USERNAME,
    val password: String = NO_PASSWORD
)

package com.kuzmin.tm_4.feature.api.domain.model.user

import com.kuzmin.tm_4.common.util.CommonConstants.NO_EMAIL
import com.kuzmin.tm_4.common.util.CommonConstants.NO_PASSWORD
import com.kuzmin.tm_4.common.util.CommonConstants.NO_USERNAME

data class User(
    val email: String = NO_EMAIL,
    val password: String = NO_PASSWORD,
    val isAdmin: Boolean = false
)

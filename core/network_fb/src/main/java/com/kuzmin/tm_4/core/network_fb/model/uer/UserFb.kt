package com.kuzmin.tm_4.core.network_fb.model.uer

import com.kuzmin.tm_4.common.util.CommonConstants.NO_NAME
import com.kuzmin.tm_4.common.util.CommonConstants.NO_TEAM_ID
import com.kuzmin.tm_4.common.util.CommonConstants.NO_UID

data class UserFb(
    val uid: String = NO_UID,
    val email: String,
    val isAdmin: Boolean = false,
    val teamId: String = NO_TEAM_ID,
    val firstName: String = NO_NAME,
    val secondName: String = NO_NAME,
    val password: String
)
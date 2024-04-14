package com.kuzmin.tm_4.feature_api.model

data class AuthUserPref(
    val username: String,
    val password: String,
    val token: String,
    val dateToken: Long,
    val remoteId: Long,
    val firstName: String,
    val lastName: String
)

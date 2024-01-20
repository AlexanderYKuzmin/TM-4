package com.kuzmin.tm_4.feature.login.data

interface AuthRepository {
    fun authenticate(username: String, password: String): String
}
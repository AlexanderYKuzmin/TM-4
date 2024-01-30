package com.kuzmin.tm_4.core.network

import com.kuzmin.tm_4.core.network.model.user.UserDto
import com.kuzmin.tm_4.core.network.model.user.UserSignInDto
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApiService {
    @POST("auth/obtain-token")
    suspend fun getUser(@Body userSignInDto: UserSignInDto): UserDto
}
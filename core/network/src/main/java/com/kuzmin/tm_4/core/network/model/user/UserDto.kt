package com.kuzmin.tm_4.core.network.model.user

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("user_id")
    @Expose
    val userIdRemote: Long,

    @Expose
    val token: String,

    @Expose
    val username: String,

    @SerializedName("user_firstname")
    @Expose
    val firstName: String,

    @SerializedName("user_lastname")
    @Expose
    val lastName: String
)

package com.kuzmin.tm_4.feature.login.domain.model

import android.os.Parcel
import android.os.Parcelable
import com.kuzmin.tm_4.common.extension.isNameConsistent
import com.kuzmin.tm_4.common.extension.isPasswordConsistent
import com.kuzmin.tm_4.common.util.CommonConstants.DEVIATION_TOKEN_LIFE_TIME
import com.kuzmin.tm_4.common.util.CommonConstants.NO_DATE
import com.kuzmin.tm_4.common.util.CommonConstants.NO_ID
import com.kuzmin.tm_4.common.util.CommonConstants.NO_NAME
import com.kuzmin.tm_4.common.util.CommonConstants.NO_PASSWORD
import com.kuzmin.tm_4.common.util.CommonConstants.NO_TOKEN
import com.kuzmin.tm_4.common.util.CommonConstants.NO_USERNAME
import com.kuzmin.tm_4.common.util.CommonConstants.TOKEN_LIFE_TIME

import java.util.Date

data class AuthUser (
    val username: String,
    val password: String,
    val token: String = NO_TOKEN,
    val dateToken: Long = NO_DATE,
    val remoteId: Long = NO_ID,
    val firstName: String = NO_NAME,
    val lastName: String = NO_NAME
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: NO_USERNAME,
        parcel.readString() ?: NO_PASSWORD,
        parcel.readString() ?: NO_TOKEN,
        parcel.readLong(),
        parcel.readLong(),
        parcel.readString() ?: NO_NAME,
        parcel.readString() ?: NO_NAME,
    ) {
    }

    val authToken: String get() = "Bearer $token"

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(username)
        parcel.writeString(password)
        parcel.writeString(token)
        parcel.writeLong(dateToken)
        parcel.writeLong(remoteId)
        parcel.writeString(firstName)
        parcel.writeString(lastName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AuthUser> {
        override fun createFromParcel(parcel: Parcel): AuthUser {
            return AuthUser(parcel)
        }

        override fun newArray(size: Int): Array<AuthUser?> {
            return arrayOfNulls(size)
        }
    }
}
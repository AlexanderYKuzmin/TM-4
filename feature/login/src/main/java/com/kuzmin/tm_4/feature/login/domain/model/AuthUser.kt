package com.kuzmin.tm_4.feature.login.domain.model

import android.os.Parcel
import android.os.Parcelable
import com.kuzmin.tm_4.feature.login.util.LoginConstants
import com.kuzmin.tm_4.feature.login.util.LoginConstants.DEVIATION_TOKEN_LIFE_TIME
import com.kuzmin.tm_4.feature.login.util.LoginConstants.NO_DATE
import com.kuzmin.tm_4.feature.login.util.LoginConstants.NO_ID
import com.kuzmin.tm_4.feature.login.util.LoginConstants.NO_NAME
import com.kuzmin.tm_4.feature.login.util.LoginConstants.NO_PASSWORD
import com.kuzmin.tm_4.feature.login.util.LoginConstants.NO_TOKEN
import com.kuzmin.tm_4.feature.login.util.LoginConstants.NO_USER
import com.kuzmin.tm_4.feature.login.util.LoginConstants.TOKEN_LIFE_TIME
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
        parcel.readString() ?: NO_USER,
        parcel.readString() ?: NO_PASSWORD,
        parcel.readString() ?: NO_TOKEN,
        parcel.readLong(),
        parcel.readLong(),
        parcel.readString() ?: NO_NAME,
        parcel.readString() ?: NO_NAME,
    ) {
    }

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

    fun isValid(): Boolean {
        with(this) {
            return if (token != NO_TOKEN && dateToken != NO_DATE) {
                isTokenValid(token, dateToken)
            } else false
        }
        return false
    }

    private fun isTokenValid(token: String, tokenDate: Long) : Boolean {
        return if (token.trim().length > 10) {
            val currentTime = Date().time
            val tokenExpirationTime = tokenDate + TOKEN_LIFE_TIME - DEVIATION_TOKEN_LIFE_TIME
            currentTime < tokenExpirationTime
        } else false
    }
}
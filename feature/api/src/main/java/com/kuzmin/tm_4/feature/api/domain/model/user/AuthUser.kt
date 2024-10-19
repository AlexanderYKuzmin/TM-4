package com.kuzmin.tm_4.feature.api.domain.model.user

import android.os.Parcel
import android.os.Parcelable
import com.kuzmin.tm_4.common.extension.toBoolean
import com.kuzmin.tm_4.common.extension.toByte
import com.kuzmin.tm_4.common.util.CommonConstants.NO_EMAIL
import com.kuzmin.tm_4.common.util.CommonConstants.NO_NAME
import com.kuzmin.tm_4.common.util.CommonConstants.NO_PASSWORD
import com.kuzmin.tm_4.common.util.CommonConstants.NO_POSITION
import com.kuzmin.tm_4.common.util.CommonConstants.NO_TEAM_ID
import com.kuzmin.tm_4.common.util.CommonConstants.NO_UID

data class AuthUser (
    val uid: String = NO_UID,
    val teamId: String = NO_TEAM_ID,
    val isAdmin: Boolean = false,
    val email: String = NO_EMAIL,
    val password: String = NO_PASSWORD,
    val position: String = NO_POSITION,
    val firstName: String = NO_NAME,
    val lastName: String = NO_NAME,
    val dataVisibility: Boolean = false
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: NO_UID,
        parcel.readString() ?: NO_TEAM_ID,
        parcel.readByte().toBoolean(),
        parcel.readString() ?: NO_EMAIL,
        parcel.readString() ?: NO_PASSWORD,
        parcel.readString() ?: NO_POSITION,
        parcel.readString() ?: NO_NAME,
        parcel.readString() ?: NO_NAME,
        parcel.readByte().toBoolean()
    ) {}

    val isRegistered: Boolean get() = uid != NO_UID
    //val authToken: String get() = "Bearer $token"

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        with(parcel) {
            writeString(uid)
            writeString(teamId)
            writeByte(isAdmin.toByte())
            writeString(email)
            writeString(password)
            writeString(position)
            writeString(firstName)
            writeString(lastName)
            writeByte(dataVisibility.toByte())
        }

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
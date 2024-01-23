package com.kuzmin.tm_4.data.local.datastore

import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object UserScheme {
    val USERNAME = stringPreferencesKey("username")
    val PASSWORD = stringPreferencesKey("password")
    val TOKEN = stringPreferencesKey("token")
    val TOKEN_DATE = longPreferencesKey("token_date")
    val REMOTE_ID = longPreferencesKey("remote_id")
    val FIRST_NAME = stringPreferencesKey("first_name")
    val LAST_NAME = stringPreferencesKey("last_name")
}
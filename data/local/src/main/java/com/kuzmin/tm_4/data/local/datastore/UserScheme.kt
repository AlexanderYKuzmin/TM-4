package com.kuzmin.tm_4.data.local.datastore

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object UserScheme {
    val EMAIL = stringPreferencesKey("email")
    val PASSWORD = stringPreferencesKey("password")
    val POSITION = stringPreferencesKey("position")
    val FIRST_NAME = stringPreferencesKey("first_name")
    val LAST_NAME = stringPreferencesKey("last_name")
    val IS_ADMIN = booleanPreferencesKey("is_admin")
    val USER_ID = stringPreferencesKey("uid")
    val TEAM_ID = stringPreferencesKey("team_id")
    val DATA_VISIBILITY = booleanPreferencesKey("data_visibility")
}
package com.kuzmin.tm_4.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.kuzmin.tm_4.common.util.CommonConstants.NO_EMAIL
import com.kuzmin.tm_4.common.util.CommonConstants.NO_NAME
import com.kuzmin.tm_4.common.util.CommonConstants.NO_PASSWORD
import com.kuzmin.tm_4.common.util.CommonConstants.NO_POSITION
import com.kuzmin.tm_4.common.util.CommonConstants.NO_TEAM_ID
import com.kuzmin.tm_4.common.util.CommonConstants.NO_UID
import com.kuzmin.tm_4.data.local.datastore.UserScheme.DATA_VISIBILITY
import com.kuzmin.tm_4.data.local.datastore.UserScheme.EMAIL
import com.kuzmin.tm_4.data.local.datastore.UserScheme.FIRST_NAME
import com.kuzmin.tm_4.data.local.datastore.UserScheme.IS_ADMIN
import com.kuzmin.tm_4.data.local.datastore.UserScheme.LAST_NAME
import com.kuzmin.tm_4.data.local.datastore.UserScheme.PASSWORD
import com.kuzmin.tm_4.data.local.datastore.UserScheme.POSITION
import com.kuzmin.tm_4.data.local.datastore.UserScheme.TEAM_ID
import com.kuzmin.tm_4.data.local.datastore.UserScheme.USER_ID
import com.kuzmin.tm_4.feature.api.domain.model.user.AuthUser
import com.kuzmin.tm_4.feature.api.domain.model.user.User
import com.kuzmin.tm_4.feature.login.api.PrefManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class PrefManagerImpl @Inject constructor(
    @ApplicationContext appContext: Context
) : PrefManager {
    val dataStore = appContext.dataStore

    override suspend fun writeData(authUser: AuthUser) {
        with(authUser) {
            dataStore.edit { prefs ->
                prefs[USER_ID] = uid
                prefs[EMAIL] = email
                prefs[PASSWORD] = password
                prefs[POSITION] = position
                prefs[TEAM_ID] = teamId
                prefs[FIRST_NAME] = firstName
                prefs[LAST_NAME] = lastName
                prefs[IS_ADMIN] = isAdmin
                prefs[DATA_VISIBILITY] = dataVisibility
            }
        }
    }
    override suspend fun readData(): AuthUser {
        with(UserScheme) {
            return dataStore.data.map { prefs ->
                val uid = prefs[USER_ID] ?: NO_UID
                val email = prefs[EMAIL] ?: NO_EMAIL
                val password = prefs[PASSWORD] ?: NO_PASSWORD
                val position = prefs[POSITION] ?: NO_POSITION
                val teamId = prefs[TEAM_ID] ?: NO_TEAM_ID
                val firstName = prefs[FIRST_NAME] ?: NO_NAME
                val lastName = prefs[LAST_NAME] ?: NO_NAME
                val isAdmin = prefs[IS_ADMIN] ?: false
                val dataVisibility = prefs[DATA_VISIBILITY] ?: false

                AuthUser(
                    uid = uid,
                    teamId = teamId,
                    isAdmin = isAdmin,
                    email = email,
                    password = password,
                    position = position,
                    firstName = firstName,
                    lastName = lastName,
                    dataVisibility = dataVisibility
                )
            }.first()
        }
    }

    override suspend fun readUserData(): User {
        with(UserScheme) {
            return dataStore.data.map { prefs ->
                val email = prefs[EMAIL] ?: NO_EMAIL
                val password = prefs[PASSWORD] ?: NO_PASSWORD

                User(email, password)
            }.first()
        }
    }

    override suspend fun clearAuthData() {
        dataStore.edit { prefs ->
            prefs.clear()
        }
    }
}
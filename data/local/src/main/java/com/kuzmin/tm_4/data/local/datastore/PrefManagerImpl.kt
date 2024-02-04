package com.kuzmin.tm_4.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.kuzmin.tm_4.common.util.CommonConstants.NO_DATE
import com.kuzmin.tm_4.common.util.CommonConstants.NO_ID
import com.kuzmin.tm_4.common.util.CommonConstants.NO_NAME
import com.kuzmin.tm_4.common.util.CommonConstants.NO_PASSWORD
import com.kuzmin.tm_4.common.util.CommonConstants.NO_TOKEN
import com.kuzmin.tm_4.common.util.CommonConstants.NO_USERNAME
import com.kuzmin.tm_4.data.local.datastore.UserScheme.FIRST_NAME
import com.kuzmin.tm_4.data.local.datastore.UserScheme.LAST_NAME
import com.kuzmin.tm_4.data.local.datastore.UserScheme.PASSWORD
import com.kuzmin.tm_4.data.local.datastore.UserScheme.REMOTE_ID
import com.kuzmin.tm_4.data.local.datastore.UserScheme.TOKEN
import com.kuzmin.tm_4.data.local.datastore.UserScheme.TOKEN_DATE
import com.kuzmin.tm_4.data.local.datastore.UserScheme.USERNAME
import com.kuzmin.tm_4.feature.login.api.PrefManager
import com.kuzmin.tm_4.feature.login.domain.model.AuthUser
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class PrefManagerImpl @Inject constructor(
    @ApplicationContext appContext: Context
) : PrefManager {
    val dataStore = appContext.dataStore

    /*suspend fun writeData(
        username: String,
        password: String,
        token: String,
        dateToken: Long,
        remoteId: Long,
        firstName: String,
        lastName: String,
    ) {
        dataStore.edit { prefs ->
            prefs[USERNAME] = username
            prefs[PASSWORD] = password
            prefs[TOKEN] = token
            prefs[TOKEN_DATE] = dateToken
            prefs[REMOTE_ID] = remoteId
            prefs[FIRST_NAME] = firstName
            prefs[LAST_NAME] = lastName
        }
    }*/

    override suspend fun writeData(authUser: AuthUser) {
        with(authUser) {
            dataStore.edit { prefs ->
                prefs[USERNAME] = username
                prefs[PASSWORD] = password   //h98dGDJx
                prefs[TOKEN] = token
                prefs[TOKEN_DATE] = dateToken
                prefs[REMOTE_ID] = remoteId
                prefs[FIRST_NAME] = firstName
                prefs[LAST_NAME] = lastName
            }
        }
    }
    override suspend fun readData(): AuthUser {
        with(UserScheme) {
            return dataStore.data.map { prefs ->
                val username = prefs[USERNAME] ?: NO_USERNAME
                val password = prefs[PASSWORD] ?: NO_PASSWORD
                val token = prefs[TOKEN] ?: NO_TOKEN
                val dateToken = prefs[TOKEN_DATE] ?: NO_DATE
                val remoteId = prefs[REMOTE_ID] ?: NO_ID
                val firstName = prefs[FIRST_NAME] ?: NO_NAME
                val lastName = prefs[LAST_NAME] ?: NO_NAME

                AuthUser(username, password, token, dateToken, remoteId, firstName, lastName)
            }.first()

        }
        //return runBlocking(Dispatchers.IO) { flowValue.first() }
    }
}
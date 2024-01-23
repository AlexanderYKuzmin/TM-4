package com.kuzmin.tm_4.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.Preferences.*
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.kuzmin.tm_4.data.local.datastore.UserScheme.FIRST_NAME
import com.kuzmin.tm_4.data.local.datastore.UserScheme.LAST_NAME
import com.kuzmin.tm_4.data.local.datastore.UserScheme.PASSWORD
import com.kuzmin.tm_4.data.local.datastore.UserScheme.REMOTE_ID
import com.kuzmin.tm_4.data.local.datastore.UserScheme.TOKEN
import com.kuzmin.tm_4.data.local.datastore.UserScheme.TOKEN_DATE
import com.kuzmin.tm_4.data.local.datastore.UserScheme.USERNAME
import com.kuzmin.tm_4.feature.login.data.PrefManager
import com.kuzmin.tm_4.feature.login.domain.model.AuthUser
import com.kuzmin.tm_4.feature.login.util.LoginConstants.NO_DATE
import com.kuzmin.tm_4.feature.login.util.LoginConstants.NO_ID
import com.kuzmin.tm_4.feature.login.util.LoginConstants.NO_NAME
import com.kuzmin.tm_4.feature.login.util.LoginConstants.NO_PASSWORD
import com.kuzmin.tm_4.feature.login.util.LoginConstants.NO_TOKEN
import com.kuzmin.tm_4.feature.login.util.LoginConstants.NO_USER
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class PrefManagerImpl @Inject constructor(
    @ApplicationContext appContext: Context
) : PrefManager {
    val dataStore = appContext.dataStore

    override suspend fun writeData(
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
    }

    override suspend fun read(): AuthUser {
        return readData(
            USERNAME,
            PASSWORD,
            TOKEN,
            TOKEN_DATE,
            REMOTE_ID,
            FIRST_NAME,
            LAST_NAME
        )
    }

    suspend fun readData(
        keyUsername: Key<String>,
        keyPassword: Key<String>,
        keyToken: Key<String>,
        keyDateToken: Key<Long>,
        keyRemoteId: Key<Long>,
        keyFirstName: Key<String>,
        keyLastName: Key<String>,
    ): AuthUser {
        val flowValue = dataStore.data.map { prefs ->
            val username = prefs[keyUsername] ?: NO_USER
            val password = prefs[keyPassword] ?: NO_PASSWORD
            val token = prefs[keyToken] ?: NO_TOKEN
            val dateToken = prefs[keyDateToken] ?: NO_DATE
            val remoteId = prefs[keyRemoteId] ?: NO_ID
            val firstName = prefs[keyFirstName] ?: NO_NAME
            val lastName = prefs[keyLastName] ?: NO_NAME

            AuthUser(username, password, token, dateToken, remoteId, firstName, lastName)
        }
        return runBlocking(Dispatchers.IO) { flowValue.first() }
    }
}
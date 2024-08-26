package com.team1.simplebank.data.dataStore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.synrgy.xdomain.model.AuthModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AuthDataStore(
    private val dataStore: DataStore<Preferences>,
) {
    fun getUserSession(): Flow<AuthModel> {
        return dataStore.data.map {
            AuthModel(
                accessToken = it[ACCESS_TOKEN_KEY] ?: "",
                refreshToken = it[REFRESH_TOKEN_KEY] ?: "",
                fullName = it[FULL_NAME_KEY] ?: "",
                username = it[USERNAME_KEY] ?: "",
                id = it[ID_KEY] ?: 0,
            )
        }
    }

    fun getNoAccount(): Flow<String?> {
        return dataStore.data.map {
            it[NO_ACCOUNT]
        }
    }

    suspend fun saveSession(sessionToken: AuthModel) {
        dataStore.edit {
            it[ACCESS_TOKEN_KEY] = sessionToken.accessToken ?: ""
            it[REFRESH_TOKEN_KEY] = sessionToken.refreshToken ?: ""
            it[FULL_NAME_KEY] = sessionToken.fullName ?: ""
            it[USERNAME_KEY] = sessionToken.username ?: ""
            it[ID_KEY] = sessionToken.id ?: 0
        }
    }

    suspend fun saveNoAccount(noAccount:String){
        dataStore.edit {
            it[NO_ACCOUNT] = noAccount
        }
    }

    suspend fun clearSession() {
        dataStore.edit {
            it.clear()
        }
    }

    companion object {
        private val ACCESS_TOKEN_KEY = stringPreferencesKey("access_token")
        private val REFRESH_TOKEN_KEY = stringPreferencesKey("refresh_token")
        private val FULL_NAME_KEY = stringPreferencesKey("full_name")
        private val USERNAME_KEY = stringPreferencesKey("user_name")
        private val ID_KEY = intPreferencesKey("id")
        private val NO_ACCOUNT = stringPreferencesKey("no_account")
    }
}
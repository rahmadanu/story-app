package com.dandev.storyapp.data.local.preference

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AuthDataStoreManager @Inject constructor(@ApplicationContext private val context: Context) {

    suspend fun setUserToken(token: String) {
        context.authDataStore.edit { preferences ->
            preferences[USER_TOKEN_KEY] = token
        }
    }

    fun getUserToken(): Flow<String> {
        return context.authDataStore.data.map { preferences ->
            preferences[USER_TOKEN_KEY] ?: ""
        }
    }

    companion object {
        private const val DATA_STORE_NAME = "auth_preferences"
        private val USER_TOKEN_KEY = stringPreferencesKey("user_token_key")

        val Context.authDataStore: DataStore<Preferences> by preferencesDataStore(name = DATA_STORE_NAME)
    }
}
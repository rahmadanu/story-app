package com.dandev.storyapp.data.local.data_source

import com.dandev.storyapp.data.local.preference.AuthDataStoreManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface AuthLocalDataSource {
    suspend fun setUserToken(token: String)
    fun getUserToken(): Flow<String>
}

class AuthLocalDataSourceImpl @Inject constructor(private val dataStoreManager: AuthDataStoreManager) :
    AuthLocalDataSource {
    override suspend fun setUserToken(token: String) {
        dataStoreManager.setUserToken(token)
    }

    override fun getUserToken(): Flow<String> {
        return dataStoreManager.getUserToken()
    }

}
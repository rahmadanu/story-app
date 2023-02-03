package com.dandev.storyapp.data.repository

import android.util.Log
import com.dandev.storyapp.data.local.data_source.AuthLocalDataSource
import com.dandev.storyapp.data.remote.data_source.AuthRemoteDataSource
import com.dandev.storyapp.data.remote.model.auth.LoginRequest
import com.dandev.storyapp.data.remote.model.auth.LoginResponse
import com.dandev.storyapp.data.remote.model.auth.RegisterRequest
import com.dandev.storyapp.data.remote.model.auth.RegisterResponse
import com.dandev.storyapp.util.wrapper.Resource
import com.dandev.storyapp.util.wrapper.proceed
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface AuthRepository {
    suspend fun registerUser(registerRequest: RegisterRequest): Resource<RegisterResponse>
    suspend fun loginUser(loginRequest: LoginRequest): Resource<LoginResponse>
    suspend fun logoutUser(): Resource<Boolean>
    fun getUserToken(): Flow<String>
}

class AuthRepositoryImpl @Inject constructor(
    private val remoteDataSource: AuthRemoteDataSource,
    private val localDataSource: AuthLocalDataSource,
): AuthRepository {
    override suspend fun registerUser(registerRequest: RegisterRequest): Resource<RegisterResponse> {
        return proceed {
            remoteDataSource.registerUser(registerRequest)
        }
    }

    override suspend fun loginUser(loginRequest: LoginRequest): Resource<LoginResponse> {
        val response = proceed {
            remoteDataSource.loginUser(loginRequest)
        }
        response.payload?.loginResult?.token?.let {
            localDataSource.setUserToken(it)
            Log.d("token", it)
        }
        return response
    }

    override suspend fun logoutUser(): Resource<Boolean> {
        return proceed {
            localDataSource.setUserToken("")
            true
        }
    }

    override fun getUserToken(): Flow<String> {
        return localDataSource.getUserToken()
    }
}
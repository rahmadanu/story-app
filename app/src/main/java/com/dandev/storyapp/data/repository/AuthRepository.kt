package com.dandev.storyapp.data.repository

import com.dandev.storyapp.data.local.data_source.AuthLocalDataSource
import com.dandev.storyapp.data.remote.data_source.AuthRemoteDataSource
import com.dandev.storyapp.data.remote.model.auth.LoginRequest
import com.dandev.storyapp.data.remote.model.auth.LoginResponse
import com.dandev.storyapp.data.remote.model.auth.RegisterRequest
import com.dandev.storyapp.data.remote.model.auth.RegisterResponse
import com.dandev.storyapp.util.wrapper.Resource
import com.dandev.storyapp.util.wrapper.proceed
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface AuthRepository {
    suspend fun registerUser(registerRequest: RegisterRequest): Resource<RegisterResponse>
    suspend fun loginUser(loginRequest: LoginRequest): Resource<LoginResponse>
    suspend fun logoutUser()
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
        }
        return response
    }

    override suspend fun logoutUser() {
        localDataSource.setUserToken("")
    }

    override fun getUserToken(): Flow<String> {
        return localDataSource.getUserToken()
    }
}
package com.dandev.storyapp.data.remote.data_source

import com.dandev.storyapp.data.remote.model.auth.LoginRequest
import com.dandev.storyapp.data.remote.model.auth.LoginResponse
import com.dandev.storyapp.data.remote.model.auth.RegisterRequest
import com.dandev.storyapp.data.remote.model.auth.RegisterResponse
import com.dandev.storyapp.data.remote.service.AuthApiService
import javax.inject.Inject

interface AuthRemoteDataSource {
    suspend fun registerUser(registerRequest: RegisterRequest): RegisterResponse
    suspend fun loginUser(loginRequest: LoginRequest): LoginResponse
}

class AuthRemoteDataSourceImpl @Inject constructor(private val apiService: AuthApiService) :
    AuthRemoteDataSource {
    override suspend fun registerUser(registerRequest: RegisterRequest): RegisterResponse {
        return apiService.registerUser(registerRequest)
    }

    override suspend fun loginUser(loginRequest: LoginRequest): LoginResponse {
        return apiService.loginUser(loginRequest)
    }
}
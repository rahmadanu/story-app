package com.dandev.storyapp.data.remote.datasource

import com.dandev.storyapp.data.remote.model.auth.LoginRequest
import com.dandev.storyapp.data.remote.model.auth.LoginResponse
import com.dandev.storyapp.data.remote.model.auth.RegisterRequest
import com.dandev.storyapp.data.remote.model.auth.RegisterResponse
import com.dandev.storyapp.data.remote.service.ApiEndPoints
import com.dandev.storyapp.data.remote.service.AuthApiService
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthRemoteDataSource {
    fun registerUser(registerRequest: RegisterRequest): RegisterResponse
    fun loginUser(loginRequest: LoginRequest): LoginResponse
}

class AuthRemoteDataSourceImpl (private val apiService: AuthApiService): AuthRemoteDataSource {
    override fun registerUser(registerRequest: RegisterRequest): RegisterResponse {
        return apiService.registerUser(registerRequest)
    }

    override fun loginUser(loginRequest: LoginRequest): LoginResponse {
        return apiService.loginUser(loginRequest)
    }
}
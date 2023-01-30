package com.dandev.storyapp.data.repository

import com.dandev.storyapp.data.remote.datasource.AuthRemoteDataSource
import com.dandev.storyapp.data.remote.model.auth.LoginRequest
import com.dandev.storyapp.data.remote.model.auth.LoginResponse
import com.dandev.storyapp.data.remote.model.auth.RegisterRequest
import com.dandev.storyapp.data.remote.model.auth.RegisterResponse

interface AuthRepository {
    fun registerUser(registerRequest: RegisterRequest): RegisterResponse
    fun loginUser(loginRequest: LoginRequest): LoginResponse
}

class AuthRepositoryImpl (private val dataSource: AuthRemoteDataSource): AuthRepository {
    override fun registerUser(registerRequest: RegisterRequest): RegisterResponse {
        return dataSource.registerUser(registerRequest)
    }

    override fun loginUser(loginRequest: LoginRequest): LoginResponse {
        return dataSource.loginUser(loginRequest)
    }
}
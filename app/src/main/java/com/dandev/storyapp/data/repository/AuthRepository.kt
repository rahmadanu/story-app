package com.dandev.storyapp.data.repository

import com.dandev.storyapp.data.remote.data_source.AuthRemoteDataSource
import com.dandev.storyapp.data.remote.model.auth.LoginRequest
import com.dandev.storyapp.data.remote.model.auth.LoginResponse
import com.dandev.storyapp.data.remote.model.auth.RegisterRequest
import com.dandev.storyapp.data.remote.model.auth.RegisterResponse
import com.dandev.storyapp.util.wrapper.Resource
import com.dandev.storyapp.util.wrapper.proceed
import javax.inject.Inject

interface AuthRepository {
    suspend fun registerUser(registerRequest: RegisterRequest): Resource<RegisterResponse>
    suspend fun loginUser(loginRequest: LoginRequest): Resource<LoginResponse>
}

class AuthRepositoryImpl @Inject constructor(private val dataSource: AuthRemoteDataSource): AuthRepository {
    override suspend fun registerUser(registerRequest: RegisterRequest): Resource<RegisterResponse> {
        return proceed {
            dataSource.registerUser(registerRequest)
        }
    }

    override suspend fun loginUser(loginRequest: LoginRequest): Resource<LoginResponse> {
        return proceed {
            dataSource.loginUser(loginRequest)
        }
    }
}
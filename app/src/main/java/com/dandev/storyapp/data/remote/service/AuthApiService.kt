package com.dandev.storyapp.data.remote.service

import com.dandev.storyapp.data.remote.model.auth.LoginRequest
import com.dandev.storyapp.data.remote.model.auth.LoginResponse
import com.dandev.storyapp.data.remote.model.auth.RegisterRequest
import com.dandev.storyapp.data.remote.model.auth.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {

    @POST(ApiEndPoints.POST_REGISTER)
    fun registerUser(@Body registerRequest: RegisterRequest): RegisterResponse

    @POST(ApiEndPoints.POST_LOGIN)
    fun loginUser(@Body loginRequest: LoginRequest): LoginResponse
}
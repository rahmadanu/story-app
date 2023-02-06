package com.dandev.storyapp.domain

import com.dandev.storyapp.data.remote.model.auth.LoginRequest
import com.dandev.storyapp.data.repository.AuthRepository
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(
    private val repository: AuthRepository,
) {
    suspend operator fun invoke(loginRequest: LoginRequest) = repository.loginUser(loginRequest)
}
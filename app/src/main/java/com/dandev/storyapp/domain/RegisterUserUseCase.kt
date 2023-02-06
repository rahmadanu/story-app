package com.dandev.storyapp.domain

import com.dandev.storyapp.data.remote.model.auth.RegisterRequest
import com.dandev.storyapp.data.repository.AuthRepository
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(
    private val repository: AuthRepository,
) {
    suspend operator fun invoke(registerRequest: RegisterRequest) =
        repository.registerUser(registerRequest)
}
package com.dandev.storyapp.domain

import com.dandev.storyapp.data.repository.AuthRepository
import javax.inject.Inject

class LogoutUserUseCase @Inject constructor(
    private val repository: AuthRepository
){
    suspend operator fun invoke() = repository.logoutUser()
}
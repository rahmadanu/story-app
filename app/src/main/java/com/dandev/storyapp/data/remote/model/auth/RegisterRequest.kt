package com.dandev.storyapp.data.remote.model.auth

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String,
)

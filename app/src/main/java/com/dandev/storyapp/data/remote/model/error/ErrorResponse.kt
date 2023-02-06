package com.dandev.storyapp.data.remote.model.error

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("status")
    val status: String? = null,
)

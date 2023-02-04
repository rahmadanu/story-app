package com.dandev.storyapp.data.remote.model.story


import com.google.gson.annotations.SerializedName

data class AddStoryResponse(
    @SerializedName("error")
    val error: Boolean? = null,
    @SerializedName("message")
    val message: String? = null
)
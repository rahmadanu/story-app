package com.dandev.storyapp.data.remote.model.story


import com.google.gson.annotations.SerializedName

data class StoriesResponse(
    @SerializedName("error")
    val error: Boolean? = null,
    @SerializedName("listStory")
    val listStory: List<Story?>? = null,
    @SerializedName("message")
    val message: String? = null
)
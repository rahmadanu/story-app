package com.dandev.storyapp.data.remote.model.story

import com.google.gson.annotations.SerializedName

data class MapsStory(
    val name: String? = null,
    val lat: Double? = null,
    val lon: Double? = null,
)
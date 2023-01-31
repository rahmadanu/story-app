package com.dandev.storyapp.data.remote.service

import com.dandev.storyapp.data.remote.model.story.StoriesResponse
import retrofit2.http.GET
import retrofit2.http.Header

interface StoryApiService {

    @GET(ApiEndPoints.GET_ALL_STORIES)
    suspend fun getAllStories(
        @Header("Authorization") token: String
    ): StoriesResponse
}
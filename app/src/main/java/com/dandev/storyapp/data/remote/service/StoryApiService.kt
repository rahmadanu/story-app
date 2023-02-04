package com.dandev.storyapp.data.remote.service

import com.dandev.storyapp.data.remote.model.story.AddStoryResponse
import com.dandev.storyapp.data.remote.model.story.StoriesResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface StoryApiService {

    @GET(ApiEndPoints.GET_ALL_STORIES)
    suspend fun getAllStories(
        @Header("Authorization") token: String
    ): StoriesResponse

    @Multipart
    @POST(ApiEndPoints.POST_NEW_STORY)
    suspend fun addNewStory(
        @Header("Authorization") token: String,
        @Part photo: MultipartBody.Part,
        @Part("description") description: RequestBody
    ): AddStoryResponse
}
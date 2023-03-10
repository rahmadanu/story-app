package com.dandev.storyapp.data.remote.service

import com.dandev.storyapp.data.remote.model.story.AddStoryResponse
import com.dandev.storyapp.data.remote.model.story.StoriesResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface StoryApiService {

    @GET(ApiEndPoints.GET_ALL_STORIES)
    suspend fun getAllStories(
        @Header("Authorization") token: String,
        @Query("page") page: Int = DEFAULT_PAGE,
        @Query("size") size: Int = SIZE_PER_PAGE,
        @Query("location") withLocation: Int = 0
    ): StoriesResponse

    @Multipart
    @POST(ApiEndPoints.POST_NEW_STORY)
    suspend fun addNewStory(
        @Header("Authorization") token: String,
        @Part photo: MultipartBody.Part,
        @Part("description") description: RequestBody,
    ): AddStoryResponse

    companion object {
        const val DEFAULT_PAGE = 1
        const val SIZE_PER_PAGE = 6
    }
}
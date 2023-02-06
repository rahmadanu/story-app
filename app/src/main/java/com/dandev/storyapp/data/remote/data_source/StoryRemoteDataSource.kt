package com.dandev.storyapp.data.remote.data_source

import com.dandev.storyapp.data.remote.model.story.AddStoryResponse
import com.dandev.storyapp.data.remote.model.story.StoriesResponse
import com.dandev.storyapp.data.remote.service.StoryApiService
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

interface StoryRemoteDataSource {
    suspend fun getAllStories(token: String): StoriesResponse
    suspend fun addNewStory(
        token: String,
        photo: MultipartBody.Part,
        description: RequestBody,
    ): AddStoryResponse
}

class StoryRemoteDataSourceImpl @Inject constructor(private val apiService: StoryApiService) :
    StoryRemoteDataSource {
    override suspend fun getAllStories(token: String): StoriesResponse {
        return apiService.getAllStories(token)
    }

    override suspend fun addNewStory(
        token: String,
        photo: MultipartBody.Part,
        description: RequestBody,
    ): AddStoryResponse {
        return apiService.addNewStory(token, photo, description)
    }
}
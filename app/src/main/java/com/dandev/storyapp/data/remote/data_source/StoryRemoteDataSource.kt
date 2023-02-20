package com.dandev.storyapp.data.remote.data_source

import com.dandev.storyapp.data.remote.model.story.AddStoryResponse
import com.dandev.storyapp.data.remote.model.story.MapsStory
import com.dandev.storyapp.data.remote.model.story.StoriesResponse
import com.dandev.storyapp.data.remote.service.StoryApiService
import com.dandev.storyapp.util.wrapper.Resource
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

interface StoryRemoteDataSource {
    suspend fun getAllStories(token: String, page: Int): StoriesResponse
    suspend fun getStoriesWithMapsInfo(token: String, size: Int): StoriesResponse
    suspend fun addNewStory(
        token: String,
        photo: MultipartBody.Part,
        description: RequestBody,
    ): AddStoryResponse
}

class StoryRemoteDataSourceImpl @Inject constructor(private val apiService: StoryApiService) :
    StoryRemoteDataSource {
    override suspend fun getAllStories(token: String, page: Int): StoriesResponse {
        return apiService.getAllStories(token = token, page = page, withLocation = 0)
    }

    override suspend fun getStoriesWithMapsInfo(token: String, size: Int): StoriesResponse {
        return apiService.getAllStories(token = token, size = size, withLocation = 1)
    }

    override suspend fun addNewStory(
        token: String,
        photo: MultipartBody.Part,
        description: RequestBody,
    ): AddStoryResponse {
        return apiService.addNewStory(token, photo, description)
    }
}
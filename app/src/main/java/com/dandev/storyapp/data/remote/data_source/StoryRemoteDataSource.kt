package com.dandev.storyapp.data.remote.data_source

import com.dandev.storyapp.data.remote.model.story.StoriesResponse
import com.dandev.storyapp.data.remote.service.StoryApiService
import javax.inject.Inject

interface StoryRemoteDataSource {
    suspend fun getAllStories(token: String): StoriesResponse
}

class StoryRemoteDataSourceImpl @Inject constructor(private val apiService: StoryApiService): StoryRemoteDataSource {
    override suspend fun getAllStories(token: String): StoriesResponse {
        return apiService.getAllStories(token)
    }
}
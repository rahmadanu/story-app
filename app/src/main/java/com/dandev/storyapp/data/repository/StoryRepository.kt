package com.dandev.storyapp.data.repository

import com.dandev.storyapp.data.remote.data_source.StoryRemoteDataSource
import com.dandev.storyapp.data.remote.model.story.StoriesResponse
import com.dandev.storyapp.util.wrapper.Resource
import com.dandev.storyapp.util.wrapper.proceed
import javax.inject.Inject

interface StoryRepository {
    suspend fun getAllStories(token: String): Resource<StoriesResponse>
}

class StoryRepositoryImpl @Inject constructor(private val dataSource: StoryRemoteDataSource): StoryRepository {
    override suspend fun getAllStories(token: String): Resource<StoriesResponse> {
        return proceed {
            dataSource.getAllStories(token)
        }
    }
}
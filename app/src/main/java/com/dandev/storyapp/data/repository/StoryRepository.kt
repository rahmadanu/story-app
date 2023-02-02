package com.dandev.storyapp.data.repository

import android.util.Log
import com.dandev.storyapp.data.local.data_source.AuthLocalDataSource
import com.dandev.storyapp.data.remote.data_source.StoryRemoteDataSource
import com.dandev.storyapp.data.remote.model.story.StoriesResponse
import com.dandev.storyapp.util.wrapper.Resource
import com.dandev.storyapp.util.wrapper.proceed
import kotlinx.coroutines.flow.first
import javax.inject.Inject

interface StoryRepository {
    suspend fun getAllStories(token: String): Resource<StoriesResponse>
}

class StoryRepositoryImpl @Inject constructor(
    private val storyRemoteDataSource: StoryRemoteDataSource,
): StoryRepository {
    override suspend fun getAllStories(token: String): Resource<StoriesResponse> {
        return proceed {
            storyRemoteDataSource.getAllStories("Bearer $token")
        }
    }
}
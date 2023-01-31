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
    suspend fun getAllStories(): Resource<StoriesResponse>
}

class StoryRepositoryImpl @Inject constructor(
    private val storyRemoteDataSource: StoryRemoteDataSource,
    private val authLocalDataSource: AuthLocalDataSource,
): StoryRepository {
    override suspend fun getAllStories(): Resource<StoriesResponse> {
        val token = authLocalDataSource.getUserToken().first()
        Log.d("token", token)
        //consider do this in use case
        val response = proceed {
            storyRemoteDataSource.getAllStories("Bearer $token")
        }
        return response
    }
}
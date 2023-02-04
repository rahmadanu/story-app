package com.dandev.storyapp.data.repository

import com.dandev.storyapp.data.remote.data_source.StoryRemoteDataSource
import com.dandev.storyapp.data.remote.model.story.AddStoryResponse
import com.dandev.storyapp.data.remote.model.story.StoriesResponse
import com.dandev.storyapp.util.wrapper.Resource
import com.dandev.storyapp.util.wrapper.proceed
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

interface StoryRepository {
    suspend fun getAllStories(token: String): Resource<StoriesResponse>
    suspend fun addNewStory(
        token: String,
        photo: File,
        description: String
    ): Resource<AddStoryResponse>
}

class StoryRepositoryImpl @Inject constructor(
    private val storyRemoteDataSource: StoryRemoteDataSource,
): StoryRepository {
    override suspend fun getAllStories(token: String): Resource<StoriesResponse> {
        return proceed {
            storyRemoteDataSource.getAllStories("Bearer $token")
        }
    }

    override suspend fun addNewStory(
        token: String,
        photo: File,
        description: String
    ): Resource<AddStoryResponse> {
        val photoRequestBody = photo.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val descriptionRequestBody = description.toRequestBody("text/plain".toMediaType())

        val imageMultipart = MultipartBody.Part.createFormData(
            "storyPhoto",
            photo.name,
            photoRequestBody
        )

        return proceed {
            storyRemoteDataSource.addNewStory(
                "Bearer $token",
                imageMultipart,
                descriptionRequestBody
            )
        }
    }
}
package com.dandev.storyapp.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.dandev.storyapp.data.remote.data_source.StoryRemoteDataSource
import com.dandev.storyapp.data.remote.model.story.AddStoryResponse
import com.dandev.storyapp.data.remote.model.story.MapsStory
import com.dandev.storyapp.data.remote.model.story.StoriesResponse
import com.dandev.storyapp.data.remote.model.story.Story
import com.dandev.storyapp.data.remote.paging.StoryPagingSource
import com.dandev.storyapp.data.remote.service.StoryApiService
import com.dandev.storyapp.util.wrapper.Resource
import com.dandev.storyapp.util.wrapper.proceed
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

interface StoryRepository {
    fun getAllStories(): LiveData<PagingData<Story>>
    suspend fun getStoriesWithMapsInfo(token: String): Resource<List<MapsStory>>
    suspend fun addNewStory(
        token: String,
        photo: File,
        description: String,
    ): Resource<AddStoryResponse>
}

class StoryRepositoryImpl @Inject constructor(
    private val storyRemoteDataSource: StoryRemoteDataSource,
    private val storyPagingSource: StoryPagingSource
) : StoryRepository {
    override fun getAllStories(): LiveData<PagingData<Story>> {
        return Pager(
            config = PagingConfig(
                pageSize = StoryApiService.SIZE_PER_PAGE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                storyPagingSource
            }
        ).liveData
    }

    override suspend fun getStoriesWithMapsInfo(token: String,): Resource<List<MapsStory>> {
        return proceed {
            storyRemoteDataSource.getStoriesWithMapsInfo(token, 10).listStory?.map {
                MapsStory(
                    name = it.name,
                    lat = it.lat,
                    lon = it.lon
                )
            }!!
        }
    }

    override suspend fun addNewStory(
        token: String,
        photo: File,
        description: String,
    ): Resource<AddStoryResponse> {
        val photoRequestBody = photo.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val descriptionRequestBody = description.toRequestBody("text/plain".toMediaType())

        val imageMultipart = MultipartBody.Part.createFormData(
            "photo",
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
package com.dandev.storyapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.PagingData
import com.dandev.storyapp.data.remote.model.story.AddStoryResponse
import com.dandev.storyapp.data.remote.model.story.MapsStory
import com.dandev.storyapp.data.remote.model.story.Story
import com.dandev.storyapp.util.DummyData
import com.dandev.storyapp.util.PagingDataTestSource
import com.dandev.storyapp.util.wrapper.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File

class FakeStoryRepository: StoryRepository {
    private val dummy = DummyData.listStory

    override fun getAllStories(): Flow<PagingData<Story>> {
        val data = PagingDataTestSource.snapshot(dummy)
        return flow { emit(data) }
    }

    override suspend fun getStoriesWithMapsInfo(token: String): Resource<List<MapsStory>> {
        return Resource.Empty()
    }

    override suspend fun addNewStory(
        token: String,
        photo: File,
        description: String,
    ): Resource<AddStoryResponse> {
        return Resource.Empty()
    }
}
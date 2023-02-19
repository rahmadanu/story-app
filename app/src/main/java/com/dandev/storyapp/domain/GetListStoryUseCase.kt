package com.dandev.storyapp.domain

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.dandev.storyapp.data.local.data_source.AuthLocalDataSource
import com.dandev.storyapp.data.remote.model.story.MapsStory
import com.dandev.storyapp.data.remote.model.story.StoriesResponse
import com.dandev.storyapp.data.remote.model.story.Story
import com.dandev.storyapp.data.repository.AuthRepository
import com.dandev.storyapp.data.repository.StoryRepository
import com.dandev.storyapp.util.wrapper.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetListStoryUseCase @Inject constructor(
    private val storyRepository: StoryRepository,
    private val authRepository: AuthRepository
) {
    operator fun invoke(): Flow<PagingData<Story>> {
        return storyRepository.getAllStories()
    }

    suspend fun getListStoryWithMap(): Resource<List<MapsStory>> {
        val token = authRepository.getUserToken().first()
        return storyRepository.getStoriesWithMapsInfo("Bearer $token")
    }
}
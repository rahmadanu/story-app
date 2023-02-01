package com.dandev.storyapp.domain

import com.dandev.storyapp.data.remote.model.story.StoriesResponse
import com.dandev.storyapp.data.repository.AuthRepository
import com.dandev.storyapp.data.repository.StoryRepository
import com.dandev.storyapp.util.wrapper.Resource
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetListStoryUseCase @Inject constructor(
    private val storyRepository: StoryRepository,
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke(): Resource<StoriesResponse> {
        val token = authRepository.getUserToken().first()
        return storyRepository.getAllStories(token)
    }
}
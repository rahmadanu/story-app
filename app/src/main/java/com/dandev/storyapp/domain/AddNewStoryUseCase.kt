package com.dandev.storyapp.domain

import com.dandev.storyapp.data.remote.model.story.AddStoryResponse
import com.dandev.storyapp.data.repository.AuthRepository
import com.dandev.storyapp.data.repository.StoryRepository
import com.dandev.storyapp.util.wrapper.Resource
import kotlinx.coroutines.flow.first
import java.io.File
import javax.inject.Inject

class AddNewStoryUseCase @Inject constructor(
    private val storyRepository: StoryRepository,
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(photo: File, description: String): Resource<AddStoryResponse> {
        val token = authRepository.getUserToken().first()
        return storyRepository.addNewStory(token, photo, description)
    }
}
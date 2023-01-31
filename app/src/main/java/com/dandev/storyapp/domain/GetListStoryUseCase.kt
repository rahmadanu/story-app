package com.dandev.storyapp.domain

import com.dandev.storyapp.data.repository.StoryRepository
import javax.inject.Inject

class GetListStoryUseCase @Inject constructor(
    private val repository: StoryRepository
) {
    suspend operator fun invoke() = repository.getAllStories()
}
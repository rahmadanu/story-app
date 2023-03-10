package com.dandev.storyapp.di

import com.dandev.storyapp.data.repository.AuthRepository
import com.dandev.storyapp.data.repository.AuthRepositoryImpl
import com.dandev.storyapp.data.repository.StoryRepository
import com.dandev.storyapp.data.repository.StoryRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    abstract fun provideStoryRepository(storyRepositoryImpl: StoryRepositoryImpl): StoryRepository
}
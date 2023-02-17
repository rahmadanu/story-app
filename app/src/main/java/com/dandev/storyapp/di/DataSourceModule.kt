package com.dandev.storyapp.di

import com.dandev.storyapp.data.local.data_source.AuthLocalDataSource
import com.dandev.storyapp.data.local.data_source.AuthLocalDataSourceImpl
import com.dandev.storyapp.data.remote.data_source.AuthRemoteDataSource
import com.dandev.storyapp.data.remote.data_source.AuthRemoteDataSourceImpl
import com.dandev.storyapp.data.remote.data_source.StoryRemoteDataSource
import com.dandev.storyapp.data.remote.data_source.StoryRemoteDataSourceImpl
import com.dandev.storyapp.data.remote.paging.StoryPagingSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun provideAuthRemoteDataSource(authRemoteDataSourceImpl: AuthRemoteDataSourceImpl): AuthRemoteDataSource

    @Binds
    abstract fun provideAuthLocalDataSource(authLocalDataSourceImpl: AuthLocalDataSourceImpl): AuthLocalDataSource

    @Binds
    abstract fun provideStoryRemoteDataSource(storyRemoteDataSourceImpl: StoryRemoteDataSourceImpl): StoryRemoteDataSource
}

@Module
@InstallIn(SingletonComponent::class)
object PagingSourceModule {

    @Provides
    @Singleton
    fun provideStoryPagingSource(storyRemoteDataSource: StoryRemoteDataSource, authLocalDataSource: AuthLocalDataSource): StoryPagingSource {
        return StoryPagingSource(storyRemoteDataSource, authLocalDataSource)
    }
}
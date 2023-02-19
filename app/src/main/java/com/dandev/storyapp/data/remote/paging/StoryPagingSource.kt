package com.dandev.storyapp.data.remote.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dandev.storyapp.data.local.data_source.AuthLocalDataSource
import com.dandev.storyapp.data.remote.data_source.StoryRemoteDataSource
import com.dandev.storyapp.data.remote.model.story.Story
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class StoryPagingSource @Inject constructor(
    private val storyRemoteDataSource: StoryRemoteDataSource,
    private val authLocalDataSource: AuthLocalDataSource
): PagingSource<Int, Story>() {
    override fun getRefreshKey(state: PagingState<Int, Story>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Story> {
        val pageIndex = params.key ?: 1
        return try {
            val token = authLocalDataSource.getUserToken().first()
            val listUsersResponse = storyRemoteDataSource.getAllStories(token = "Bearer $token", page = pageIndex)
            val listStory = listUsersResponse.listStory!!
            Log.d("paging", token)
            Log.d("paging", listStory.toString())
            LoadResult.Page(
                data = listStory,
                prevKey = if (pageIndex == 1) null else pageIndex,
                nextKey = if (listStory.isEmpty()) null else pageIndex + 1
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}
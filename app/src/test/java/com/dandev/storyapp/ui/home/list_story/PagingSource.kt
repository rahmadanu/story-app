package com.dandev.storyapp.ui.home.list_story

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.dandev.storyapp.data.remote.model.story.Story
import androidx.paging.PagingSource
import androidx.paging.PagingState

class PagingSource: PagingSource<Int, LiveData<List<Story>>>() {
    override fun getRefreshKey(state: PagingState<Int, LiveData<List<Story>>>): Int? {
        return 0
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LiveData<List<Story>>> {
        return LoadResult.Page(emptyList(), 0, 1)
    }

    companion object {
        fun snapshot(items: List<Story>): PagingData<Story> {
            return PagingData.from(items)
        }
    }
}
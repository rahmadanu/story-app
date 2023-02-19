package com.dandev.storyapp.ui.home.list_story

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.cachedIn
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import app.cash.turbine.test
import com.dandev.storyapp.data.remote.model.story.Story
import com.dandev.storyapp.data.repository.AuthRepository
import com.dandev.storyapp.data.repository.AuthRepositoryImpl
import com.dandev.storyapp.data.repository.FakeStoryRepository
import com.dandev.storyapp.data.repository.StoryRepository
import com.dandev.storyapp.domain.GetListStoryUseCase
import com.dandev.storyapp.domain.LogoutUserUseCase
import com.dandev.storyapp.ui.home.list_story.adapter.ListStoryAdapter
import com.dandev.storyapp.util.DummyData
import com.dandev.storyapp.util.MainCoroutineRule
import com.dandev.storyapp.util.PagingDataTestSource
import com.dandev.storyapp.util.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ListStoryViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Mock
    private lateinit var getListStoryUseCase: GetListStoryUseCase
    @Mock
    private lateinit var logoutUserUseCase: LogoutUserUseCase
    private lateinit var listStoryViewModel: ListStoryViewModel

    @Before
    fun setUp() {
        listStoryViewModel = ListStoryViewModel(getListStoryUseCase, logoutUserUseCase)
    }

    @Test
    fun `when get list story should return success`() = runTest {
        val dummyListStory = DummyData.listStory
        val data = PagingData.from(dummyListStory)
        val expectedStory = flow { emit(data)  }

        `when`(getListStoryUseCase.invoke()).thenReturn(expectedStory)

        listStoryViewModel.storyResponse.test {
            listStoryViewModel.getListStory()
            Mockito.verify(getListStoryUseCase).invoke()

            val differ = AsyncPagingDataDiffer(
                diffCallback = MyDiffCallback(),
                updateCallback = NoopListCallback(),
                workerDispatcher = Dispatchers.Main
            )
            awaitItem()
            val actual= awaitItem()
            differ.submitData(actual)

            advanceUntilIdle()
            assertNotNull(actual)
            assertEquals(dummyListStory, differ.snapshot().items)
        }
    }
}

class NoopListCallback : ListUpdateCallback {
    override fun onChanged(position: Int, count: Int, payload: Any?) {}
    override fun onMoved(fromPosition: Int, toPosition: Int) {}
    override fun onInserted(position: Int, count: Int) {}
    override fun onRemoved(position: Int, count: Int) {}
}

class MyDiffCallback : DiffUtil.ItemCallback<Story>() {
    override fun areItemsTheSame(oldItem: Story, newItem: Story): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Story, newItem: Story): Boolean {
        return oldItem == newItem
    }
}
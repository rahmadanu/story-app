package com.dandev.storyapp.ui.home.list_story

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dandev.storyapp.data.remote.model.story.StoriesResponse
import com.dandev.storyapp.data.remote.model.story.Story
import com.dandev.storyapp.domain.GetListStoryUseCase
import com.dandev.storyapp.domain.LogoutUserUseCase
import com.dandev.storyapp.util.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListStoryViewModel @Inject constructor(
    private val getListStoryUseCase: GetListStoryUseCase,
    private val logoutUserUseCase: LogoutUserUseCase,
) : ViewModel() {

    private val _logoutResponse = MutableLiveData<Resource<Boolean>>()
    val logoutResponse: LiveData<Resource<Boolean>> get() = _logoutResponse

    fun getListStory(): LiveData<PagingData<Story>> = getListStoryUseCase.invoke().cachedIn(viewModelScope)

    fun logoutUser() {
        _logoutResponse.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            val response = logoutUserUseCase()
            viewModelScope.launch(Dispatchers.Main) {
                _logoutResponse.postValue(response)
            }
        }
    }
}
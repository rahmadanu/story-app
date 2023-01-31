package com.dandev.storyapp.ui.home.list_story

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dandev.storyapp.data.remote.model.story.StoriesResponse
import com.dandev.storyapp.domain.GetListStoryUseCase
import com.dandev.storyapp.util.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListStoryViewModel @Inject constructor(
    private val getListStoryUseCase: GetListStoryUseCase
): ViewModel() {
    private val _listStoryResponse = MutableLiveData<Resource<StoriesResponse>>()
    val listStoryResponse: LiveData<Resource<StoriesResponse>> get() = _listStoryResponse

    fun getAllStories() {
        _listStoryResponse.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            val response = getListStoryUseCase()
            viewModelScope.launch(Dispatchers.Main) {
                _listStoryResponse.postValue(response)
            }
        }
    }
}
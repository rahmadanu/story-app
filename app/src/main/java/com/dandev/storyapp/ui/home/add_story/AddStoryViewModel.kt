package com.dandev.storyapp.ui.home.add_story

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dandev.storyapp.data.remote.model.story.AddStoryResponse
import com.dandev.storyapp.domain.AddNewStoryUseCase
import com.dandev.storyapp.util.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class AddStoryViewModel @Inject constructor(
    private val addNewStoryUseCase: AddNewStoryUseCase,
) : ViewModel() {
    private var _addNewStoryResponse = MutableLiveData<Resource<AddStoryResponse>>()
    val addNewStoryResponse: LiveData<Resource<AddStoryResponse>> get() = _addNewStoryResponse

    fun addNewStory(photo: File, description: String) {
        _addNewStoryResponse.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            val response = addNewStoryUseCase(photo, description)
            viewModelScope.launch(Dispatchers.Main) {
                _addNewStoryResponse.postValue(response)
            }
        }
    }
}
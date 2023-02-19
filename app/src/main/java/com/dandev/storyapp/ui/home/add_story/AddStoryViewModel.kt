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
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class AddStoryViewModel @Inject constructor(
    private val addNewStoryUseCase: AddNewStoryUseCase,
) : ViewModel() {
    private var _addNewStoryResponse = MutableSharedFlow<Resource<AddStoryResponse>>()
    val addNewStoryResponse = _addNewStoryResponse.asSharedFlow()

    fun addNewStory(photo: File, description: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _addNewStoryResponse.emit(Resource.Loading())
            val response = addNewStoryUseCase(photo, description)
            viewModelScope.launch(Dispatchers.Main) {
                response.collect {
                    _addNewStoryResponse.emit(it)
                }
            }
        }
    }
}
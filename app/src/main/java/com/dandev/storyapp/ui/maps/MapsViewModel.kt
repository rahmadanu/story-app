package com.dandev.storyapp.ui.maps

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dandev.storyapp.data.remote.model.story.MapsStory
import com.dandev.storyapp.domain.GetListStoryUseCase
import com.dandev.storyapp.util.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapsViewModel @Inject constructor(
    private val getListStoryUseCase: GetListStoryUseCase
): ViewModel() {
    private val _mapsInfo = MutableLiveData<Resource<List<MapsStory>>>()
    val mapsInfo: LiveData<Resource<List<MapsStory>>> get() = _mapsInfo

    fun getListStoryWithMap() {
        _mapsInfo.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            val response = getListStoryUseCase.getListStoryWithMap()
            viewModelScope.launch(Dispatchers.Main) {
                _mapsInfo.postValue(response)
            }
        }
    }
}
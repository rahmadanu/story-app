package com.dandev.storyapp.ui.auth.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dandev.storyapp.data.remote.model.auth.RegisterRequest
import com.dandev.storyapp.data.remote.model.auth.RegisterResponse
import com.dandev.storyapp.domain.RegisterUserUseCase
import com.dandev.storyapp.util.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUserUseCase: RegisterUserUseCase
): ViewModel() {
    private val _registerResponse = MutableLiveData<Resource<RegisterResponse>>()
    val registerResponse: LiveData<Resource<RegisterResponse>> get() = _registerResponse

    fun registerUser(registerRequest: RegisterRequest) {
        _registerResponse.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            val response = registerUserUseCase(registerRequest)
            viewModelScope.launch(Dispatchers.Main) {
                _registerResponse.postValue(response)
            }
        }
    }
}
package com.dandev.storyapp.ui.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dandev.storyapp.data.remote.model.auth.LoginRequest
import com.dandev.storyapp.data.remote.model.auth.LoginResponse
import com.dandev.storyapp.domain.LoginUserUseCase
import com.dandev.storyapp.util.wrapper.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUserUseCase: LoginUserUseCase,
) : ViewModel() {
    private val _loginResponse = MutableLiveData<Resource<LoginResponse>>()
    val loginResponse: LiveData<Resource<LoginResponse>> = _loginResponse

    fun loginUser(loginRequest: LoginRequest) {
        _loginResponse.postValue(Resource.Loading())
        viewModelScope.launch(Dispatchers.IO) {
            val response = loginUserUseCase(loginRequest)
            viewModelScope.launch(Dispatchers.Main) {
                _loginResponse.postValue(response)
            }
        }
    }
}
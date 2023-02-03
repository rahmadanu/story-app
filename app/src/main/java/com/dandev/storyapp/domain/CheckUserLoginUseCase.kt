package com.dandev.storyapp.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.dandev.storyapp.data.repository.AuthRepository
import com.dandev.storyapp.util.wrapper.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CheckUserLoginUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(): LiveData<Resource<String>> = liveData {
        delay(1000)
        val token = repository.getUserToken().first()
        if (token != "") {
            emit(Resource.Success("User already logged in"))
        } else {
            emit(Resource.Empty("User is NOT logged in"))
        }
    }
}
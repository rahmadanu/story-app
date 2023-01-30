package com.dandev.storyapp.util.wrapper

import com.dandev.storyapp.data.remote.model.error.ErrorResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.HttpException

suspend fun <T> proceed(coroutines: suspend () -> T): Resource<T> {
    return try {
        Resource.Success(coroutines.invoke())
    } catch (e: Exception) {
        when (e) {
            is HttpException -> {
                val errorMessageResponseType = object : TypeToken<ErrorResponse>() {}.type
                val error: ErrorResponse = Gson().fromJson(e.response()?.errorBody()?.charStream(), errorMessageResponseType)
                Resource.Error(e, "${error.status}: ${error.message}")
            } else -> {
                Resource.Error(e, e.message)
            }
        }
    }
}
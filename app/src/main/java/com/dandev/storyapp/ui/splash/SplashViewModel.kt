package com.dandev.storyapp.ui.splash

import androidx.lifecycle.ViewModel
import com.dandev.storyapp.domain.CheckUserLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val checkUserLoginUseCase: CheckUserLoginUseCase,
) : ViewModel() {

    fun isLoggedIn() = checkUserLoginUseCase()
}
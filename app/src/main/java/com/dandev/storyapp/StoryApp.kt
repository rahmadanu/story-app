package com.dandev.storyapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class StoryApp : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: StoryApp private set
    }
}
package com.dandev.storyapp.util.date

import androidx.annotation.StringRes
import com.dandev.storyapp.StoryApp

object Strings {
    fun get(@StringRes stringRes: Int, vararg formatArgs: Any = emptyArray()): String {
        return StoryApp.instance.getString(stringRes, *formatArgs)
    }
}
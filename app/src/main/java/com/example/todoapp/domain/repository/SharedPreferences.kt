package com.example.todoapp.domain.repository

import android.content.SharedPreferences
import com.example.todoapp.domain.state.GeneralState
import com.example.todoapp.domain.util.Constants
import javax.inject.Inject

class SharedPreferences @Inject constructor (
    private val prefs: SharedPreferences,
    private val editor: SharedPreferences.Editor
) {

    private val userState = GeneralState()

    fun saveTheme(value: Boolean) {
        editor.putBoolean(Constants.MODE_THEME, value).apply()
    }

    fun getThemeSchema() : Boolean {
        return prefs.getBoolean(Constants.MODE_THEME, userState.onDarkScheme)
    }

}
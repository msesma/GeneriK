package com.paradigmadigital.repository

import android.content.SharedPreferences
import javax.inject.Inject

class Preferences
@Inject
constructor(
        private val sharedPreferences: SharedPreferences
) {
    companion object {
        private val IS_LOGGED_IN_KEY = "IS_LOGGED_IN_KEY"
    }

    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    var isloggedIn: Boolean
        get() = sharedPreferences.getBoolean(IS_LOGGED_IN_KEY, false)
        set(value) = editor.putBoolean(IS_LOGGED_IN_KEY, value).apply()
}
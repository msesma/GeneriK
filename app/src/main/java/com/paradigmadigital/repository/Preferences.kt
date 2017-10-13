package com.paradigmadigital.repository

import android.content.SharedPreferences
import javax.inject.Inject

class Preferences
@Inject
constructor(
        val sharedPreferences: SharedPreferences
) {
    companion object {
        val IS_LOGGED_IN_KEY = "IS_LOGGED_IN_KEY"
    }

    val editor = sharedPreferences.edit()

    var isloggedIn: Boolean
        get() = sharedPreferences.getBoolean(IS_LOGGED_IN_KEY, false)
        set(value) = editor.putBoolean(IS_LOGGED_IN_KEY, value).apply()
}
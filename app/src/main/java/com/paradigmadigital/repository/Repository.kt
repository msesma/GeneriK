package com.paradigmadigital.repository

import javax.inject.Inject


class Repository
@Inject
constructor(
        val preferences: Preferences
) {

    fun isLoggedIn() = preferences.isloggedIn

    fun setLoggedIn(logged: Boolean) {
        preferences.isloggedIn = logged
    }

    fun getUserName(): String {
        return "Monica"
        //TODO
    }

}
package com.paradigmadigital.repository

import android.arch.lifecycle.LiveData
import com.paradigmadigital.domain.db.UserDao
import com.paradigmadigital.domain.entities.User
import javax.inject.Inject


class Repository
@Inject
constructor(
        private val preferences: Preferences,
        private val securePreferences: SecurePreferences,
        private val userDao: UserDao
) {

    fun isLoggedIn() = preferences.isloggedIn

    fun setLoggedIn(logged: Boolean) {
        preferences.isloggedIn = logged
        if (!logged) securePreferences.password = ""
    }

    fun getUser(): LiveData<User> {
        return userDao.get()
    }

    fun getPass() = securePreferences.password

    fun setUser(user: User, pass: String) {
        //TODO: Send data to backend (This does not request an SMS), on Ok insert on DB or if error report back
        securePreferences.password = pass
        userDao.insert(user)
    }
}
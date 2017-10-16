package com.paradigmadigital.repository

import android.arch.lifecycle.LiveData
import com.paradigmadigital.domain.db.UserDao
import com.paradigmadigital.domain.entities.User
import javax.inject.Inject


class Repository
@Inject
constructor(
        private val preferences: Preferences,
        private val userDao: UserDao
) {

    fun isLoggedIn() = preferences.isloggedIn

    fun setLoggedIn(logged: Boolean) {
        preferences.isloggedIn = logged
    }

    fun getUser(): LiveData<User> {
        return userDao.get()
    }

    fun setUser(user: User, pass: String) {
        //TODO: Send data to backend (This does not request an SMS), on Ok insert on DB or if error report back
        userDao.insert(user)
    }
}
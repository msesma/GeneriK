package com.paradigmadigital.repository

import android.arch.lifecycle.LiveData
import com.paradigmadigital.domain.db.UserDao
import com.paradigmadigital.domain.entities.User
import com.paradigmadigital.repository.providers.RegisterProvider
import javax.inject.Inject


class Repository
@Inject
constructor(
        private val preferences: Preferences,
        private val registerProvider: RegisterProvider,
        private val networkResultLiveData: NetworkResultLiveData,
        private val userDao: UserDao,
        private val securePreferences: SecurePreferences
) {

    fun getErrors(): LiveData<NetworkResult> = networkResultLiveData

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
        registerProvider.registerUser(user, pass) { networkResultLiveData.setNetworkResult(it) }
    }
}
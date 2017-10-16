package com.paradigmadigital.ui.main

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.paradigmadigital.domain.entities.User
import com.paradigmadigital.platform.AndroidApplication
import com.paradigmadigital.repository.Repository
import javax.inject.Inject

data class MainViewModel
constructor(
        val app: Application
) : AndroidViewModel(app) {

    @Inject lateinit var repository: Repository

    val userLiveData: LiveData<User>

    init {
        (app as AndroidApplication).applicationComponent.inject(this)
        userLiveData = repository.getUser()
    }
}

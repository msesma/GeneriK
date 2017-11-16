package com.paradigmadigital.ui.viewmodels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.paradigmadigital.platform.AndroidApplication
import com.paradigmadigital.repository.NetworkResult
import com.paradigmadigital.repository.LoginRepository
import javax.inject.Inject

data class ResultViewModel
constructor(
        private val app: Application
) : AndroidViewModel(app) {

    @Inject lateinit var repository: LoginRepository

    val result: LiveData<NetworkResult>

    init {
        (app as AndroidApplication).applicationComponent.inject(this)
        result = repository.getErrors()
    }
}
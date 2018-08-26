package eu.sesma.generik.ui.viewmodels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import eu.sesma.generik.platform.AndroidApplication
import eu.sesma.generik.repository.NetworkResult
import eu.sesma.generik.repository.LoginRepository
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
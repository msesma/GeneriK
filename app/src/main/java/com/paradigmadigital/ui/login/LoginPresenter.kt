package com.paradigmadigital.ui.login

import com.paradigmadigital.navigation.Navigator
import com.paradigmadigital.repository.LoginRepository
import com.paradigmadigital.ui.login.LoginDecorator.Companion.REQUEST_LOGIN
import com.paradigmadigital.ui.viewmodels.ResultViewModel
import com.paradigmadigital.usecases.LoginUseCase
import javax.inject.Inject


class LoginPresenter
@Inject
constructor(
        private val navigator: Navigator,
        private val loginUseCase: LoginUseCase,
//        private val fingerprintManager: FingerprintManager,
        private val repository: LoginRepository
) {
    private var decorator: LoginUserInterface? = null

    private val delegate = object : LoginUserInterface.Delegate {

        override fun onLogin(email: String, pass: String) = loginUseCase.execute(email, pass, REQUEST_LOGIN)

        override fun onForgotPassword(email: String) = navigator.navigateToChangePassword(email)

        override fun onLoggedIn() {
            if (repository.isLoggedIn()) navigator.navigateToMain()
        }
    }

    fun initialize(decorator: LoginUserInterface, resultViewModel: ResultViewModel) {
        this.decorator = decorator
//        repository.timeoutRequireLoginCheck()
        this.decorator?.initialize(delegate, resultViewModel)
//        fingerprintManager.startAuth { onFingerprintAuth(it) }
    }

//    private fun onFingerprintAuth(result: Boolean) {
//        if (result) loginUseCase.execute(repository.getEmail(), repository.getPass(), REQUEST_LOGIN)
//    }

    fun dispose() {
        this.decorator = null
    }
}
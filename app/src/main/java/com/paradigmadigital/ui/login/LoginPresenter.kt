package com.paradigmadigital.ui.login

import com.paradigmadigital.navigation.Navigator
import com.paradigmadigital.repository.Repository
import com.paradigmadigital.ui.ResultViewModel
import com.paradigmadigital.ui.login.LoginDecorator.Companion.REQUEST_LOGIN
import javax.inject.Inject


class LoginPresenter
@Inject
constructor(
        private val navigator: Navigator,
        private val repository: Repository
) {

    private var decorator: LoginUserInterface? = null

    private val delegate = object : LoginUserInterface.Delegate {

        override fun onLogin(email: String, pass: String) {
            repository.login(email, pass, REQUEST_LOGIN)
        }

        override fun onForgotPassword(email: String) {
            repository.setUser(email)
            navigator.navigateToChangePassword()
        }

        override fun onLoggedIn(): Boolean {
            val loggedIn = repository.isLoggedIn()
            if (loggedIn) navigator.navigateToMain()
            return loggedIn
        }
    }

    fun initialize(decorator: LoginUserInterface, resultViewModel: ResultViewModel) {
        this.decorator = decorator
        this.decorator?.initialize(delegate, resultViewModel)
    }

    fun dispose() {
        this.decorator = null
    }
}
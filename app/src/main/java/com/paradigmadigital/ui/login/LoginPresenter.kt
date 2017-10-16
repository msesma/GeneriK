package com.paradigmadigital.ui.login

import com.paradigmadigital.navigation.Navigator
import com.paradigmadigital.repository.Repository
import javax.inject.Inject


class LoginPresenter
@Inject
constructor(
        val navigator: Navigator,
        val repository: Repository
) {

    private var decorator: LoginUserInterface? = null

    private val delegate = object : LoginUserInterface.Delegate {
        override fun onLogin(email: String, pass: String) {
            repository.setLoggedIn(true)
            navigator.navigateToMain()
            //TODO Real login
        }

        override fun onForgotPassword() {
            navigator.navigateToInputCode()
        }
    }

    fun initialize(decorator: LoginUserInterface) {
        this.decorator = decorator
        this.decorator?.initialize(delegate)
    }

    fun dispose() {
        this.decorator = null
    }
}
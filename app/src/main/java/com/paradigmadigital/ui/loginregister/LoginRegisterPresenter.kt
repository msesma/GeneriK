package com.paradigmadigital.ui.loginregister

import com.paradigmadigital.navigation.Navigator
import javax.inject.Inject


class LoginRegisterPresenter
@Inject
constructor(
        private val navigator: Navigator
) {

    private var decorator: LoginRegisterUserInterface? = null

    private val delegate = object : LoginRegisterUserInterface.Delegate {

        override fun onLogin() = navigator.navigateToLogin()

        override fun onRegister() = navigator.navigateToRegister()

        override fun onTerms() = navigator.navigateToTerms()
    }

    fun initialize(decorator: LoginRegisterUserInterface) {
        this.decorator = decorator
        this.decorator?.initialize(delegate)
    }

    fun dispose() {
        this.decorator = null
    }
}

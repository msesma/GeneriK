package com.paradigmadigital.ui.login

import com.paradigmadigital.navigation.Navigator
import com.paradigmadigital.ui.login.LoginDecorator.Companion.REQUEST_LOGIN
import com.paradigmadigital.ui.viewmodels.ResultViewModel
import com.paradigmadigital.ui.viewmodels.UserViewModel
import com.paradigmadigital.usecases.ForgotPassUseCase
import com.paradigmadigital.usecases.LoginUseCase
import javax.inject.Inject


class LoginPresenter
@Inject
constructor(
        private val navigator: Navigator,
        private val loginUseCase: LoginUseCase,
        private val forgotPassUseCase: ForgotPassUseCase
) {

    private var decorator: LoginUserInterface? = null

    private val delegate = object : LoginUserInterface.Delegate {

        override fun onLogin(email: String, pass: String) = loginUseCase.execute(email, pass, REQUEST_LOGIN)

        override fun onForgotPassword(email: String) = forgotPassUseCase.execute(email)

        override fun onLoggedIn() = navigator.navigateToMain()
    }

    fun initialize(decorator: LoginUserInterface, userViewModel: UserViewModel, resultViewModel: ResultViewModel) {
        this.decorator = decorator
        this.decorator?.initialize(delegate, userViewModel, resultViewModel)
    }

    fun dispose() {
        this.decorator = null
    }
}
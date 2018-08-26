package eu.sesma.generik.ui.login

import eu.sesma.generik.navigation.Navigator
import eu.sesma.generik.repository.LoginRepository
import eu.sesma.generik.ui.login.LoginDecorator.Companion.REQUEST_LOGIN
import eu.sesma.generik.ui.viewmodels.ResultViewModel
import eu.sesma.generik.usecases.LoginUseCase
import javax.inject.Inject


class LoginPresenter
@Inject
constructor(
        private val navigator: Navigator,
        private val loginUseCase: LoginUseCase,
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
        this.decorator?.initialize(delegate, resultViewModel)
    }

    fun dispose() {
        this.decorator = null
    }
}
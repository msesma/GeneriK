package eu.sesma.generik.ui.register

import eu.sesma.generik.api.model.Login
import eu.sesma.generik.navigation.Navigator
import eu.sesma.generik.ui.register.RegisterDecorator.Companion.REQUEST_REGISTER
import eu.sesma.generik.ui.viewmodels.ResultViewModel
import eu.sesma.generik.usecases.RegisterUserUseCase
import javax.inject.Inject

class RegisterPresenter
@Inject
constructor(
        navigator: Navigator,
        useCase: RegisterUserUseCase
) {

    private var decorator: RegisterUserInterface? = null

    private val delegate = object : RegisterUserInterface.Delegate {

        override fun onRegister(name: String, tel: String, email: String) {
            val user = Login(name = name, phone = tel, email = email)
            useCase.execute(user, REQUEST_REGISTER)
        }

        override fun onRegistered(email: String, pass: String) =
                navigator.navigateToInputCode(email, pass)
    }

    fun initialize(decorator: RegisterUserInterface, resultViewModel: ResultViewModel) {
        this.decorator = decorator
        this.decorator?.initialize(delegate, resultViewModel)
    }

    fun dispose() {
        this.decorator = null
    }

    fun setPhone(phone: String?) {
        if (phone != null) decorator?.setPhone(phone)
    }
}
package com.paradigmadigital.ui.register

import com.paradigmadigital.api.model.Login
import com.paradigmadigital.navigation.Navigator
import com.paradigmadigital.ui.register.RegisterDecorator.Companion.REQUEST_REGISTER
import com.paradigmadigital.ui.viewmodels.ResultViewModel
import com.paradigmadigital.usecases.RegisterUserUseCase
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
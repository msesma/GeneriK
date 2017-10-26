package com.paradigmadigital.ui.register

import com.paradigmadigital.domain.entities.User
import com.paradigmadigital.navigation.Navigator
import com.paradigmadigital.ui.register.RegisterDecorator.Companion.REQUEST_REGISTER
import com.paradigmadigital.ui.viewmodels.ResultViewModel
import com.paradigmadigital.usecases.RegisterUserUseCase
import java.util.*
import javax.inject.Inject

class RegisterPresenter
@Inject
constructor(
        navigator: Navigator,
        useCase: RegisterUserUseCase
) {

    private var decorator: RegisterUserInterface? = null

    private val delegate = object : RegisterUserInterface.Delegate {

        override fun onRegister(name: String, tel: String, email: String, pass: String) {
            val user = User(name = name, phone = tel, email = email, registerDate = Date())
            useCase.execute(user, pass, REQUEST_REGISTER)
        }

        override fun onRegistered() = navigator.navigateToInputCode()
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
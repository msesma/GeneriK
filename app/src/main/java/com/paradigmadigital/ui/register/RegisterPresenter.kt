package com.paradigmadigital.ui.register

import com.paradigmadigital.domain.entities.User
import com.paradigmadigital.navigation.Navigator
import com.paradigmadigital.usecases.RegisterUserUseCase
import java.util.*
import javax.inject.Inject

class RegisterPresenter
@Inject
constructor(
        val navigator: Navigator,
        useCase: RegisterUserUseCase
) {

    private var decorator: RegisterUserInterface? = null

    private val delegate = object : RegisterUserInterface.Delegate {
        override fun onRegister(name: String, tel: String, email: String, pass: String) {
            val user = User(name, Date(), tel, email)
            useCase.execute(user, pass)
            //TODO Wait for BE confirming operation
            navigator.navigateToInputCode()
        }
    }

    fun initialize(decorator: RegisterUserInterface) {
        this.decorator = decorator
        this.decorator?.initialize(delegate)
    }

    fun dispose() {
        this.decorator = null
    }
}
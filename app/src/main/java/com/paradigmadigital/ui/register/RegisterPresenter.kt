package com.paradigmadigital.ui.register

import com.paradigmadigital.navigation.Navigator
import com.paradigmadigital.repository.Repository
import javax.inject.Inject

class RegisterPresenter
@Inject
constructor(
        val navigator: Navigator,
        val repository: Repository
) {

    private var decorator: RegisterUserInterface? = null

    private val delegate = object : RegisterUserInterface.Delegate {
        override fun onRegister(name: String, tel: String, email: String, pass: String) {
            //TODO
            navigator.navigateToLoginRegister()
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
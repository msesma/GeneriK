package com.paradigmadigital.ui.profile

import com.paradigmadigital.api.model.Login
import com.paradigmadigital.navigation.Navigator
import com.paradigmadigital.ui.register.RegisterDecorator.Companion.REQUEST_REGISTER
import com.paradigmadigital.ui.viewmodels.ResultViewModel
import com.paradigmadigital.usecases.RegisterUserUseCase
import javax.inject.Inject

class ProfilePresenter
@Inject
constructor(
        navigator: Navigator,
        useCase: RegisterUserUseCase
) {

    private var decorator: ProfileUserInterface? = null

    private val delegate = object : ProfileUserInterface.Delegate {

        override fun onProfileEdit(name: String, tel: String, email: String) {
            val user = Login(name = name, phone = tel, email = email)
            useCase.execute(user, REQUEST_REGISTER)
        }

        override fun onProfileEdited(email: String, pass: String) =
                navigator.navigateToInputCode(email, pass)
    }

    fun initialize(decorator: ProfileUserInterface, resultViewModel: ResultViewModel) {
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
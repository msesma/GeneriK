package com.paradigmadigital.ui.inputcode

import com.paradigmadigital.navigation.Navigator
import com.paradigmadigital.ui.viewmodels.ResultViewModel
import com.paradigmadigital.usecases.RequestCodeUseCase
import com.paradigmadigital.usecases.SetPassUseCase
import javax.inject.Inject


class InputCodePresenter
@Inject
constructor(
        private val navigator: Navigator,
        private val smsManager: SmsManager,
        private val setPassUseCase: SetPassUseCase,
        private val requestCodeUseCase: RequestCodeUseCase
) {

    private var decorator: InputCodeUserInterface? = null
    private lateinit var email: String
    private lateinit var pass: String

    private val delegate = object : InputCodeUserInterface.Delegate {
        override fun onCode(code: String) = setPassUseCase.execute(
                code,
                email,
                pass,
                InputCodeDecorator.REQUEST_SET_PASS)

        override fun onSendNew() = requestCodeUseCase.execute(InputCodeDecorator.REQUEST_CODE)

        override fun onCodeSent() = navigator.navigateToLoginRegister()
    }

    fun initialize(
            decorator: InputCodeUserInterface,
            resultViewModel: ResultViewModel,
            email: String,
            pass: String) {
        this.decorator = decorator
        this.decorator?.initialize(delegate, resultViewModel)
        this.email = email
        this.pass = pass

        requestSmsCode(decorator)
    }

    fun dispose() {
        smsManager.dispose()
        this.decorator = null
    }

    private fun requestSmsCode(decorator: InputCodeUserInterface) {
        smsManager.initialize { decorator.autoComplete(it) }
        requestCodeUseCase.execute(InputCodeDecorator.REQUEST_CODE)
    }
}
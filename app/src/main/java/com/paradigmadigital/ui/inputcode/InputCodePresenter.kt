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

    private val delegate = object : InputCodeUserInterface.Delegate {
        override fun onCode(code: String) = setPassUseCase.execute(code, InputCodeDecorator.REQUEST_SET_PASS)

        override fun onSendNew() = requestCodeUseCase.execute(InputCodeDecorator.REQUEST_CODE)

        override fun onCodeSent(sucess: Boolean) = navigator.navigateToLoginRegister()
    }

    fun initialize(decorator: InputCodeUserInterface, resultViewModel: ResultViewModel) {
        this.decorator = decorator
        this.decorator?.initialize(delegate, resultViewModel)

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
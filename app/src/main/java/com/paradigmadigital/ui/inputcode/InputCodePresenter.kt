package com.paradigmadigital.ui.inputcode

import com.paradigmadigital.navigation.Navigator
import com.paradigmadigital.repository.Repository
import com.paradigmadigital.ui.ResultViewModel
import javax.inject.Inject


class InputCodePresenter
@Inject
constructor(
        private val navigator: Navigator,
        private val smsManager: SmsManager,
        private val repository: Repository
) {

    private var decorator: InputCodeUserInterface? = null

    private val delegate = object : InputCodeUserInterface.Delegate {
        override fun onCode(code: String) {
            repository.setPass(code, InputCodeDecorator.REQUEST_SET_PASS)
        }

        override fun onSendNew() {
            repository.requestCode(InputCodeDecorator.REQUEST_CODE)
        }

        override fun onCodeSent(sucess: Boolean) {
            navigator.navigateToLoginRegister()
        }
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
        repository.requestCode(InputCodeDecorator.REQUEST_CODE)
    }
}
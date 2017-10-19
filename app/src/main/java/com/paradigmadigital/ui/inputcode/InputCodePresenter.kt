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
    private var fromRegister = false

    private val delegate = object : InputCodeUserInterface.Delegate {
        override fun onCode(code: String) {
            repository.setPass(InputCodeDecorator.REQUEST_SET_PASS)
        }

        override fun onSendNew() {
            decorator?.autoComplete("123456")
            repository.requestCode(InputCodeDecorator.REQUEST_CODE)
        }

        override fun onCodeSent(sucess: Boolean) {
            if (fromRegister || !sucess) navigator.navigateToLoginRegister() else navigator.navigateToChangePassword()
        }
    }

    fun initialize(decorator: InputCodeUserInterface, fromRegister: Boolean, resultViewModel: ResultViewModel) {
        this.decorator = decorator
        this.decorator?.initialize(delegate, resultViewModel)
        this.fromRegister = fromRegister

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
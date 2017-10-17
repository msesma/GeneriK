package com.paradigmadigital.ui.inputcode

import com.paradigmadigital.navigation.Navigator
import javax.inject.Inject


class InputCodePresenter
@Inject
constructor(
        private val navigator: Navigator,
        private val smsManager: SmsManager
) {

    private var decorator: InputCodeUserInterface? = null
    private var fromRegister = false

    private val delegate = object : InputCodeUserInterface.Delegate {
        override fun onCode(code: String) {
            //TODO verify code
            if (fromRegister) navigator.navigateToLoginRegister() else navigator.navigateToChangePassword()
        }

        override fun onSendNew() {
            decorator?.autoComplete("123456")
            //TODO  Ask the backend to send a new code
        }
    }

    fun initialize(decorator: InputCodeUserInterface, fromRegister: Boolean) {
        this.decorator = decorator
        this.decorator?.initialize(delegate)
        this.fromRegister = fromRegister

        requestSmsCode(decorator)
    }

    fun dispose() {
        smsManager.dispose()
        this.decorator = null
    }

    private fun requestSmsCode(decorator: InputCodeUserInterface) {
        smsManager.initialize { decorator.autoComplete(it) }
        //TODO  Ask the backend to send a code
    }
}
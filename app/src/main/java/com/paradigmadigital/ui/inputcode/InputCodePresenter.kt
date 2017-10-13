package com.paradigmadigital.ui.inputcode

import com.paradigmadigital.navigation.Navigator
import com.paradigmadigital.repository.Repository
import javax.inject.Inject


class InputCodePresenter
@Inject
constructor(
        val navigator: Navigator,
        val repository: Repository
) {

    private var decorator: InputCodeUserInterface? = null

    private val delegate = object : InputCodeUserInterface.Delegate {
        override fun onCode(code: String) {
            //TODO verify code
            navigator.navigateToChangePassword()
        }

        override fun onSendNew() {
            decorator?.autoComplete("123456")
            //TODO  Real stuff
        }

    }

    fun initialize(decorator: InputCodeUserInterface) {
        this.decorator = decorator
        this.decorator?.initialize(delegate)
    }

    fun dispose() {
        this.decorator = null
    }
}
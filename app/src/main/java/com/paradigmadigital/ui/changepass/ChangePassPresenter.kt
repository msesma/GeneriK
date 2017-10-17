package com.paradigmadigital.ui.changepass

import com.paradigmadigital.navigation.Navigator
import javax.inject.Inject

class ChangePassPresenter
@Inject
constructor(
        val navigator: Navigator
) {

    private var decorator: ChangePassUserInterface? = null

    private val delegate = object : ChangePassUserInterface.Delegate {
        override fun onNewPass(pass: String) {
            navigator.closeActivity()
            //TODO update password
        }
    }

    fun initialize(decorator: ChangePassUserInterface) {
        this.decorator = decorator
        this.decorator?.initialize(delegate)
    }

    fun dispose() {
        this.decorator = null
    }
}
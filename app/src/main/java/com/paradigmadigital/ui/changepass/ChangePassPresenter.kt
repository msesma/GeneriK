package com.paradigmadigital.ui.changepass

import com.paradigmadigital.usecases.ChangePassUseCase
import javax.inject.Inject

class ChangePassPresenter
@Inject
constructor(
        useCase: ChangePassUseCase
) {

    private var decorator: ChangePassUserInterface? = null

    private val delegate = object : ChangePassUserInterface.Delegate {
        override fun onNewPass(pass: String) = useCase.execute(pass)
    }

    fun initialize(decorator: ChangePassUserInterface) {
        this.decorator = decorator
        this.decorator?.initialize(delegate)
    }

    fun dispose() {
        this.decorator = null
    }
}
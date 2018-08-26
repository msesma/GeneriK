package eu.sesma.generik.ui.changepass

import eu.sesma.generik.usecases.ChangePassUseCase
import javax.inject.Inject

class ChangePassPresenter
@Inject
constructor(
        useCase: ChangePassUseCase
) {

    private var decorator: ChangePassUserInterface? = null
    private lateinit var email: String

    private val delegate = object : ChangePassUserInterface.Delegate {
        override fun onNewPass(pass: String) = useCase.execute(email, pass)
    }

    fun initialize(decorator: ChangePassUserInterface, email: String) {
        this.decorator = decorator
        this.decorator?.initialize(delegate)
        this.email = email
    }

    fun dispose() {
        this.decorator = null
    }
}
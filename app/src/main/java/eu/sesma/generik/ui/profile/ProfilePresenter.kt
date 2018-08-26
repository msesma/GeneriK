package eu.sesma.generik.ui.profile

import eu.sesma.generik.api.model.Login
import eu.sesma.generik.domain.entities.User
import eu.sesma.generik.navigation.Navigator
import eu.sesma.generik.repository.LoginRepository
import eu.sesma.generik.usecases.ProfileUseCase
import javax.inject.Inject

class ProfilePresenter
@Inject
constructor(
        navigator: Navigator,
        val repository: LoginRepository,
        useCase: ProfileUseCase
) {

    private var decorator: ProfileUserInterface? = null

    private val delegate = object : ProfileUserInterface.Delegate {

        override fun onProfileEdit(name: String, tel: String, email: String) {
            val user = Login(name = name, phone = tel, email = email)
            useCase.execute(user).subscribe({ decorator?.onResult(it) }, { throw it })
        }

        override fun onProfileEdited() =
                navigator.navigateToMain()
    }

    fun initialize(decorator: ProfileUserInterface) {
        this.decorator = decorator
        this.decorator?.initialize(delegate, repository.getUser() ?: User())
    }

    fun dispose() {
        this.decorator = null
    }

    fun setPhone(phone: String?) {
        if (phone != null) decorator?.setPhone(phone)
    }
}
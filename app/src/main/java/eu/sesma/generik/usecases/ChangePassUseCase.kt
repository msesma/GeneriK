package eu.sesma.generik.usecases

import eu.sesma.generik.navigation.Navigator
import eu.sesma.generik.repository.LoginRepository
import javax.inject.Inject

class ChangePassUseCase
@Inject constructor(
        private val repository: LoginRepository,
        private val navigator: Navigator
){

    fun execute(email: String, pass: String){
        navigator.closeActivity()
        navigator.navigateToInputCode(email, pass)
    }
}
package com.paradigmadigital.usecases

import com.paradigmadigital.navigation.Navigator
import com.paradigmadigital.repository.LoginRepository
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
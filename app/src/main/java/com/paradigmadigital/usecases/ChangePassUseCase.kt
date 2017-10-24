package com.paradigmadigital.usecases

import com.paradigmadigital.navigation.Navigator
import com.paradigmadigital.repository.Repository
import javax.inject.Inject

class ChangePassUseCase
@Inject constructor(
        private val repository: Repository,
        private val navigator: Navigator
){

    fun execute(pass: String){
        repository.updatePass(pass)
        navigator.closeActivity()
        navigator.navigateToInputCode()
    }
}
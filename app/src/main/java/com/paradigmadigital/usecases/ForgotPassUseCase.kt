package com.paradigmadigital.usecases

import com.paradigmadigital.navigation.Navigator
import com.paradigmadigital.repository.Repository
import javax.inject.Inject

class ForgotPassUseCase
@Inject constructor(
        private val repository: Repository,
        private val navigator: Navigator
) {

    fun execute(email: String) {
        repository.setUser(email)
        navigator.navigateToChangePassword()
    }
}
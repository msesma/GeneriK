package com.paradigmadigital.ui.login

import com.paradigmadigital.ui.viewmodels.ResultViewModel
import com.paradigmadigital.ui.viewmodels.UserViewModel


interface LoginUserInterface {

    fun initialize(delegate: Delegate, userViewModel: UserViewModel, resultViewModel: ResultViewModel)

    interface Delegate {

        fun onLogin(email: String, pass: String)

        fun onForgotPassword(email: String)

        fun onLoggedIn()

    }
}
package com.paradigmadigital.ui.login

import com.paradigmadigital.ui.viewmodels.ResultViewModel


interface LoginUserInterface {

    fun initialize(delegate: Delegate, resultViewModel: ResultViewModel)

    interface Delegate {

        fun onLogin(email: String, pass: String)

        fun onForgotPassword(email: String)

        fun onLoggedIn()

    }
}
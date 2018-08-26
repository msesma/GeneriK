package eu.sesma.generik.ui.login

import eu.sesma.generik.ui.viewmodels.ResultViewModel


interface LoginUserInterface {

    fun initialize(delegate: Delegate, resultViewModel: ResultViewModel)

    interface Delegate {

        fun onLogin(email: String, pass: String)

        fun onForgotPassword(email: String)

        fun onLoggedIn()

    }
}
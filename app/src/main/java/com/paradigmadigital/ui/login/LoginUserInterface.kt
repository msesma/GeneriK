package com.paradigmadigital.ui.login


interface LoginUserInterface {

    fun initialize(delegate: Delegate)

    interface Delegate {

        fun onLogin(email: String, pass: String)

        fun onForgotPassword(email: String)

    }
}
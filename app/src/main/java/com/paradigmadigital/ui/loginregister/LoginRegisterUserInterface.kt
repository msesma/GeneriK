package com.paradigmadigital.ui.loginregister


interface LoginRegisterUserInterface {

    fun initialize(delegate: Delegate)

    interface Delegate {

        fun onLogin()

        fun onRegister()

        fun onTerms()
    }
}
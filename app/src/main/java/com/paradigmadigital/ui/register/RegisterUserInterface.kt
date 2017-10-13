package com.paradigmadigital.ui.register

interface RegisterUserInterface {

    fun initialize(delegate: Delegate)

    interface Delegate {

        fun onRegister(name: String, tel: String, email: String, pass: String)

    }
}
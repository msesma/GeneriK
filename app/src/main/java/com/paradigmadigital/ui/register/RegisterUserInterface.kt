package com.paradigmadigital.ui.register

import com.paradigmadigital.ui.viewmodels.ResultViewModel

interface RegisterUserInterface {

    fun initialize(delegate: Delegate, resultViewModel: ResultViewModel)

    fun setPhone(phone: String)

    interface Delegate {

        fun onRegister(name: String, tel: String, email: String, pass: String)

        fun onRegistered()

    }
}
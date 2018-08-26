package eu.sesma.generik.ui.register

import eu.sesma.generik.ui.viewmodels.ResultViewModel

interface RegisterUserInterface {

    fun initialize(delegate: Delegate, resultViewModel: ResultViewModel)

    fun setPhone(phone: String)

    interface Delegate {

        fun onRegister(name: String, tel: String, email: String)

        fun onRegistered(email: String, pass: String)

    }
}
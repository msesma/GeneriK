package com.paradigmadigital.ui.profile

import com.paradigmadigital.ui.viewmodels.ResultViewModel

interface ProfileUserInterface {

    fun initialize(delegate: Delegate, resultViewModel: ResultViewModel)

    fun setPhone(phone: String)

    interface Delegate {

        fun onProfileEdit(name: String, tel: String, email: String)

        fun onProfileEdited(email: String, pass: String)

    }
}
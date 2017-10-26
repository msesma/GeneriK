package com.paradigmadigital.ui.main

import com.paradigmadigital.ui.viewmodels.UserViewModel

interface MainUserInterface {

    fun initialize(delegate: Delegate, viewModel: UserViewModel)

    interface Delegate {

    }
}
package com.paradigmadigital.ui.main

import com.paradigmadigital.ui.viewmodels.UserViewModel
import javax.inject.Inject

class MainPresenter
@Inject
constructor(
) {
    private var decorator: MainUserInterface? = null
    private val delegate = object : MainUserInterface.Delegate {}

    fun initialize(decorator: MainUserInterface, viewModel: UserViewModel) {
        this.decorator = decorator
        this.decorator?.initialize(delegate, viewModel)
    }

    fun dispose() {
        this.decorator = null
    }
}
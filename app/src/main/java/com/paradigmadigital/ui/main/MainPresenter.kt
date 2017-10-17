package com.paradigmadigital.ui.main

import javax.inject.Inject

class MainPresenter
@Inject
constructor(
) {
    private var decorator: MainUserInterface? = null
    private val delegate = object : MainUserInterface.Delegate {}

    fun initialize(decorator: MainUserInterface, viewModel: MainViewModel) {
        this.decorator = decorator
        this.decorator?.initialize(delegate, viewModel)
    }

    fun dispose() {
        this.decorator = null
    }
}
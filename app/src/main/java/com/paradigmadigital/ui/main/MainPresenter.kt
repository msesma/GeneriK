package com.paradigmadigital.ui.main

import javax.inject.Inject

class MainPresenter
@Inject
constructor(
) {
    private var decorator: MainUserInterface? = null
    private val delegate = object : MainUserInterface.Delegate {}

    fun initialize(decorator: MainUserInterface) {
        this.decorator = decorator
        this.decorator?.initialize(delegate)
    }

    fun dispose() {
        this.decorator = null
    }
}
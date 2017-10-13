package com.paradigmadigital.ui.main

import com.paradigmadigital.repository.Repository
import javax.inject.Inject

class MainPresenter
@Inject
constructor(
        val repository: Repository
) {

    private var decorator: MainUserInterface? = null

    private val delegate = object : MainUserInterface.Delegate {

    }

    fun initialize(decorator: MainUserInterface) {
        this.decorator = decorator
        this.decorator?.initialize(delegate, repository.getUserName())
    }

    fun dispose() {
        this.decorator = null
    }
}
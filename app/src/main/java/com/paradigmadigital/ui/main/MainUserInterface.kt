package com.paradigmadigital.ui.main

interface MainUserInterface {

    fun initialize(delegate: Delegate, viewModel: MainViewModel)

    interface Delegate {

    }
}
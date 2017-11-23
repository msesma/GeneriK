package com.paradigmadigital.ui.pin


interface PinUserInterface {

    fun initialize(delegate: Delegate)

    interface Delegate {

        fun onCode(pin: String)

    }
}
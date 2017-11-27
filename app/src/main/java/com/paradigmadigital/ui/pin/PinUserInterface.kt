package com.paradigmadigital.ui.pin


interface PinUserInterface {

    fun initialize(delegate: Delegate)

    fun showFingerprint(show: Boolean)

    interface Delegate {

        fun onCode(pin: String)

    }
}
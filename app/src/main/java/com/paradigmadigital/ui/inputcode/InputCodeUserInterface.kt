package com.paradigmadigital.ui.inputcode


interface InputCodeUserInterface {

    fun initialize(delegate: Delegate)

    fun autoComplete(text: String)

    interface Delegate {

        fun onCode(code: String)

        fun onSendNew()

    }
}
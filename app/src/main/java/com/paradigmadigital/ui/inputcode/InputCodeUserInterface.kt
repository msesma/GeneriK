package com.paradigmadigital.ui.inputcode

import com.paradigmadigital.ui.viewmodels.ResultViewModel


interface InputCodeUserInterface {

    fun initialize(delegate: Delegate, resultViewModel: ResultViewModel)

    fun autoComplete(text: String)

    interface Delegate {

        fun onCode(code: String)

        fun onSendNew()

        fun onCodeSent(sucess: Boolean)

    }
}
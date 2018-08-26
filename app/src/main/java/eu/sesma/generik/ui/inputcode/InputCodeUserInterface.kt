package eu.sesma.generik.ui.inputcode

import eu.sesma.generik.ui.viewmodels.ResultViewModel


interface InputCodeUserInterface {

    fun initialize(delegate: Delegate, resultViewModel: ResultViewModel)

    fun autoComplete(text: String)

    interface Delegate {

        fun onCode(code: String)

        fun onSendNew()

        fun onCodeSent()

    }
}
package eu.sesma.generik.ui.loginregister


interface LoginRegisterUserInterface {

    fun initialize(delegate: Delegate)

    interface Delegate {

        fun onLogin()

        fun onRegister()

        fun onTerms()
    }
}
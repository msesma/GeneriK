package com.paradigmadigital.ui

import com.paradigmadigital.navigation.Navigator
import com.paradigmadigital.repository.preferences.Preferences
import javax.inject.Inject


abstract class LoggedInBaseActivity : BaseActivity() {

    @Inject
    lateinit var preferences: Preferences
    @Inject
    lateinit var navigator: Navigator

    override fun onResume() {
        super.onResume()

        if (preferences.requirePin && preferences.timeout) {
            navigator.navigateToPin()
            navigator.closeActivity()
            return
        }

        preferences.timeout = true
    }

}
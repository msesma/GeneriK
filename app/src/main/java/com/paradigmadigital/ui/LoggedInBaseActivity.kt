package com.paradigmadigital.ui

import com.paradigmadigital.repository.preferences.Preferences
import javax.inject.Inject


abstract class LoggedInBaseActivity : BaseActivity() {

    @Inject
    lateinit var preferences: Preferences

    override fun onResume() {
        super.onResume()
        preferences.timeout = true
    }

}
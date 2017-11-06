package com.paradigmadigital.repository.preferences

import android.os.Bundle
import com.paradigmadigital.R

class NotificationPreferenceFragment : SettingsFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.pref_notification)
        setHasOptionsMenu(true)
    }
}

package eu.sesma.generik.repository.preferences

import android.os.Bundle
import eu.sesma.generik.R

class NotificationPreferenceFragment : SettingsFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.pref_notification)
        setHasOptionsMenu(true)
    }
}

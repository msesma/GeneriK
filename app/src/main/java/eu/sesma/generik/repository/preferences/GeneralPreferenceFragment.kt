package eu.sesma.generik.repository.preferences

import android.os.Bundle
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat
import eu.sesma.generik.R

class GeneralPreferenceFragment : SettingsFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.pref_general)
        setHasOptionsMenu(true)

        val fingerprintManagerCompat = FingerprintManagerCompat.from(activity)
        val hwDetected = fingerprintManagerCompat.isHardwareDetected
        val hasEnrolledFp = fingerprintManagerCompat.hasEnrolledFingerprints()
        if (!hwDetected || !hasEnrolledFp) {
            val screen = getPreferenceScreen()
            screen.removePreference(findPreference(activity.getString(R.string.allow_fingerprint)))
        }
    }
}
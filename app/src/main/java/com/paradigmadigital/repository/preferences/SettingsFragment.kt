package com.paradigmadigital.repository.preferences

import android.content.Intent
import android.preference.ListPreference
import android.preference.Preference
import android.preference.PreferenceFragment
import android.preference.PreferenceManager
import android.view.MenuItem

open class SettingsFragment : PreferenceFragment() {

    protected fun bindPreferenceSummaryToValue(preference: Preference) {
        preference.onPreferenceChangeListener = Preference
                .OnPreferenceChangeListener { _, value -> this.bindPreferenceSummaryToValue(preference, value) }
        bindPreferenceSummaryToValue(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.context)
                        .getString(preference.key, ""))
    }

    private fun bindPreferenceSummaryToValue(preference: Preference, value: Any?): Boolean {
        if (preference is ListPreference) {
            bindListPreferenceSummaryToValue(preference, value)
            return true
        }

        preference.summary = value!!.toString()
        return true
    }

    private fun bindListPreferenceSummaryToValue(preference: ListPreference, value: Any?) {
        val index = preference.findIndexOfValue(value as String?)
        if (index == -1) {
            preference.summary = ""
            return
        }
        val entry = preference.entries[index].toString()
        preference.summary = entry.toString()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            startActivity(Intent(activity, SettingsActivity::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}

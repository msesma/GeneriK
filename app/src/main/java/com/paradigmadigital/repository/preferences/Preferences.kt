package com.paradigmadigital.repository.preferences

import android.content.Context
import android.content.SharedPreferences
import com.paradigmadigital.R
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class Preferences @Inject constructor(
        private val preferences: SharedPreferences,
        private val context: Context
) {
    companion object {
        val TIMEOUT_KEY = "key_timeout"
        val TIMEOUT = TimeUnit.MINUTES.toMillis(5)
    }

    val requireLogin: Boolean
        get() = preferences.getBoolean(context.getString(R.string.require_login), false)

    val allowFingerPrint: Boolean
        get() = preferences.getBoolean(context.getString(R.string.allow_fingerprint), false)

    var timeout: Boolean
        get() = System.currentTimeMillis() - preferences.getLong(TIMEOUT_KEY, 0) > TIMEOUT
        set(value) {
            preferences.edit()
                    .putLong(TIMEOUT_KEY, if (value) System.currentTimeMillis() else 0)
                    .apply()
        }

}

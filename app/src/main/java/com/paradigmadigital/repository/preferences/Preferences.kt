package com.paradigmadigital.repository.preferences

import android.content.Context
import android.content.SharedPreferences
import com.paradigmadigital.R
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class Preferences @Inject constructor(
        private val preferences: SharedPreferences,
        private val context: Context
) {
    companion object {
        private val TIMEOUT_KEY = "key_timeout"
        private val CODE_KEY = "key_code"
        private val CODE_TIME_KEY = "key_code_time"
        private val CODE_EMAIL_KEY = "key_code_email"
        private val TIMEOUT = TimeUnit.MINUTES.toMillis(1)
    }

    val requirePin: Boolean
        get() = preferences.getBoolean(context.getString(R.string.require_pin), false) &&
                preferences.getString(context.getString(R.string.pin), null).length == 4

    val allowFingerPrint: Boolean
        get() = preferences.getBoolean(context.getString(R.string.allow_fingerprint), false)

    val pin: String?
        get() = preferences.getString(context.getString(R.string.pin), null)

    val code
        get() = preferences.getString(CODE_KEY, "")

    val codeEmail
        get() = preferences.getString(CODE_EMAIL_KEY, "")

    val codeTime
        get() = Date(preferences.getLong(CODE_TIME_KEY, 0))

    var timeout: Boolean
        get() = System.currentTimeMillis() - preferences.getLong(TIMEOUT_KEY, 0) > TIMEOUT
        set(value) {
            preferences.edit()
                    .putLong(TIMEOUT_KEY, if (value) System.currentTimeMillis() else 0)
                    .apply()
        }

    fun setCode(code: String, time: Date, email: String) {
        preferences.edit()
                .putString(CODE_KEY, code)
                .putLong(CODE_TIME_KEY, time.time)
                .putString(CODE_EMAIL_KEY, email)
                .apply()
    }

    fun logout() {
        preferences.edit().clear().apply()
    }

}

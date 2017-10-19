package com.paradigmadigital.repository

import com.q42.qlassified.Qlassified
import javax.inject.Inject

//https://github.com/Q42/Qlassified-Android

class SecurePreferences @Inject constructor(
) {
    companion object {
        val PASS_KEY = "key_one"
    }

    var password: String
        get() = Qlassified.Service.getString(PASS_KEY) ?: ""
        set(value) {
            Qlassified.Service.put(PASS_KEY, value)
        }
}
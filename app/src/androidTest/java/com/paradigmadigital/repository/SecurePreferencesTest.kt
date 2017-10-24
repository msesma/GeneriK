package com.paradigmadigital.repository

import android.support.test.runner.AndroidJUnit4
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SecurePreferencesTest {

    private val securePreferences = SecurePreferences()

    @Test
    fun storeAndRetrieveAString() {
        val text = "TestTextStored"

        securePreferences.password = text

        assertThat(securePreferences.password).isEqualTo(text)
    }
}
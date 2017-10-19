package com.paradigmadigital.ui.loginregister

import android.app.Activity
import android.app.Instrumentation
import android.net.Uri
import android.os.SystemClock
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.Intents.intending
import android.support.test.espresso.intent.matcher.IntentMatchers.*
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.paradigmadigital.R
import com.paradigmadigital.ui.login.LoginActivity
import com.paradigmadigital.ui.register.RegisterActivity
import org.hamcrest.Matchers.not
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginRegisterActivityInstrumentedTest {
    companion object {
        private val TERMS_URL = "https://www.paradigmadigital.com/quienes-somos/"
    }

    @get:Rule
    var activityTestRule = ActivityTestRule(LoginRegisterActivity::class.java)


    @Test
    @Throws(Exception::class)
    fun useAppContext() {

        val appContext = InstrumentationRegistry.getTargetContext()

        assertEquals("com.paradigmadigital", appContext.packageName)
    }


    @Test
    fun startLoginActivityOnLoginClick() {
        Intents.init()

        SystemClock.sleep(100)
        Espresso.onView(withId(R.id.bt_login))
                .check(matches(isDisplayed()))
                .perform(click())

        Intents.intended(hasComponent(LoginActivity::class.java.name))
        Intents.release()
    }


    @Test
    fun startRegisterActivityOnRegisterClick() {
        Intents.init()

        Espresso.onView(withId(R.id.bt_register))
                .check(matches(isDisplayed()))
                .perform(click())

        Intents.intended(hasComponent(RegisterActivity::class.java.name))
        Intents.release()
    }


    @Test
    fun startTermsWebViewOnTermsClick() {
        Intents.init()
        intending(not(isInternal())).respondWith(Instrumentation.ActivityResult(Activity.RESULT_OK, null))

        SystemClock.sleep(100)
        Espresso.onView(withId(R.id.bt_terms))
                .check(matches(isDisplayed()))
                .perform(click())

        Intents.intended(hasData(Uri.parse(TERMS_URL)))
        Intents.release()
    }
}
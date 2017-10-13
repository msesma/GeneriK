package com.paradigmadigital.navigation

import android.app.Activity
import android.app.Instrumentation
import android.net.Uri
import android.os.SystemClock
import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.matcher.IntentMatchers
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.paradigmadigital.postoscarrefour.R
import com.paradigmadigital.ui.loginregister.LoginRegisterActivity
import com.paradigmadigital.ui.main.MainActivity
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DrawerInstrumentedTest {

    companion object {
        private val TERMS_URL = "https://www.paradigmadigital.com/quienes-somos/"
        private val WAIT_DRAWER_OPEN_MILLIS = 500L
    }

    @get:Rule
    var activityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun openDrawer() {
        activityTestRule.activity
        Espresso.onView(ViewMatchers.withContentDescription("Open"))
                .perform(ViewActions.click())
        SystemClock.sleep(WAIT_DRAWER_OPEN_MILLIS)

        Espresso.onView(ViewMatchers.withText(R.string.logout))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun startTermsWebViewOnTermsClick() {
        activityTestRule.activity
        Intents.init()
        Intents.intending(Matchers.not(IntentMatchers.isInternal())).respondWith(Instrumentation.ActivityResult(Activity.RESULT_OK, null))

        Espresso.onView(ViewMatchers.withContentDescription("Open"))
                .perform(ViewActions.click())
        SystemClock.sleep(WAIT_DRAWER_OPEN_MILLIS)
        Espresso.onView(ViewMatchers.withText(R.string.terms))
                .perform(ViewActions.click())

        Intents.intended(IntentMatchers.hasData(Uri.parse(DrawerInstrumentedTest.TERMS_URL)))
        Intents.release()
    }

    @Test
    fun startLoginRegisterWebViewOnLogoutClick() {
        activityTestRule.activity
        Intents.init()
        Intents.intending(Matchers.not(IntentMatchers.isInternal())).respondWith(Instrumentation.ActivityResult(Activity.RESULT_OK, null))

        Espresso.onView(ViewMatchers.withContentDescription("Open"))
                .perform(ViewActions.click())
        SystemClock.sleep(WAIT_DRAWER_OPEN_MILLIS)
        Espresso.onView(ViewMatchers.withText(R.string.logout))
                .perform(ViewActions.click())

        Intents.intended(IntentMatchers.hasComponent(LoginRegisterActivity::class.java.name))
        Intents.release()
    }
}
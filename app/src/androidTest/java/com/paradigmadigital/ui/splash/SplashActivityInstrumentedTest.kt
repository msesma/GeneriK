package com.paradigmadigital.ui.splash

import android.os.SystemClock
import android.preference.PreferenceManager
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.matcher.IntentMatchers
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.paradigmadigital.R
import com.paradigmadigital.ui.loginregister.LoginRegisterActivity
import com.paradigmadigital.ui.main.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SplashActivityInstrumentedTest {

    @get:Rule
    var activityTestRule = ActivityTestRule(SplashActivity::class.java, true, false)

    @Test
    fun goToLoginRegisterIfNotLoggedIn() {
        val pref = PreferenceManager.getDefaultSharedPreferences(InstrumentationRegistry.getTargetContext())
        pref.edit().putBoolean("IS_LOGGED_IN_KEY", false).apply()
        activityTestRule.launchActivity(null)
        Intents.init()

        SystemClock.sleep(500)
        Intents.intended(IntentMatchers.hasComponent(LoginRegisterActivity::class.java.name))
        Intents.release()
    }

    @Test
    fun goToMAinIfLoggedIn() {
        val pref = PreferenceManager.getDefaultSharedPreferences(InstrumentationRegistry.getTargetContext())
        pref.edit().putBoolean("IS_LOGGED_IN_KEY", true).apply()
        activityTestRule.launchActivity(null)
        Intents.init()


        SystemClock.sleep(500)
        Intents.intended(IntentMatchers.hasComponent(MainActivity::class.java.name))
        Intents.release()
    }

    @Test
    fun ShowSplashScreen() {
        activityTestRule.launchActivity(null)

        Espresso.onView(ViewMatchers.withId(R.id.logo))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

}
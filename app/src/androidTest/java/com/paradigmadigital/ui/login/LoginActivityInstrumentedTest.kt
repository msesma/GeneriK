package com.paradigmadigital.ui.login

import android.os.SystemClock
import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.action.ViewActions.replaceText
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.matcher.IntentMatchers
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.hasErrorText
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.paradigmadigital.R
import com.paradigmadigital.testutils.setFocus
import com.paradigmadigital.ui.changepass.ChangePassActivity
import com.paradigmadigital.ui.main.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class LoginActivityInstrumentedTest {

    @get:Rule
    var activityTestRule = ActivityTestRule(LoginActivity::class.java)


    @Test
    fun goToMainOnLoginClickWithCorrectData() {
        Intents.init()

        Espresso.onView(ViewMatchers.withId(R.id.et_email))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(replaceText("test@email.com"))
        SystemClock.sleep(100)
        Espresso.onView(ViewMatchers.withId(R.id.et_pass))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(replaceText("12345"))
        SystemClock.sleep(100)
        Espresso.onView(ViewMatchers.withId(R.id.bt_login))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(ViewActions.click())

        SystemClock.sleep(1500)
        Intents.intended(IntentMatchers.hasComponent(MainActivity::class.java.name))
        Intents.release()
    }

    @Test
    fun showErrorOnLoginClickWithBadMail() {
         Espresso.onView(ViewMatchers.withId(R.id.et_email))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(replaceText("test@email"))
        SystemClock.sleep(100)
        Espresso.onView(ViewMatchers.withId(R.id.et_pass))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(replaceText("12345"))
        SystemClock.sleep(100)
        Espresso.onView(ViewMatchers.withId(R.id.bt_login))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(ViewActions.click())

        SystemClock.sleep(200)
        Espresso.onView(ViewMatchers.withId(R.id.et_email))
                .check(matches(hasErrorText("Invalid email format")))
    }

    @Test
    fun showErrorOnLoginClickWithNoMail() {
         Espresso.onView(ViewMatchers.withId(R.id.et_email))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(replaceText(""))
        SystemClock.sleep(100)
        Espresso.onView(ViewMatchers.withId(R.id.et_pass))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(replaceText("12345"))
        SystemClock.sleep(100)
        Espresso.onView(ViewMatchers.withId(R.id.bt_login))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(ViewActions.click())

        SystemClock.sleep(200)
        Espresso.onView(ViewMatchers.withId(R.id.et_email))
                .check(matches(hasErrorText("Invalid email format")))
    }

    @Test
    fun showErrorOnLoginClickWithNoPass() {
        Espresso.onView(ViewMatchers.withId(R.id.et_email))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(replaceText("test@email.com"))
        SystemClock.sleep(100)
        Espresso.onView(ViewMatchers.withId(R.id.et_pass))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(replaceText(""))
        SystemClock.sleep(100)
        Espresso.onView(ViewMatchers.withId(R.id.bt_login))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.et_pass))
                .check(matches(hasErrorText("Password cannot be empty")))
    }

    @Test
    fun forgotDialogOpensAndCloses() {
        Espresso.onView(ViewMatchers.withId(R.id.et_email))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(replaceText("test@email.com"))
        SystemClock.sleep(100)
        Espresso.onView(ViewMatchers.withId(R.id.et_pass))
                .perform(setFocus())

        val btForgot = Espresso.onView(ViewMatchers.withId(R.id.bt_forgot))
        btForgot.check(matches(ViewMatchers.isDisplayed()))
        btForgot.perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withText(R.string.confirm_pass_change)).check(matches(ViewMatchers.isDisplayed()))

        val cancelButton = Espresso.onView(ViewMatchers.withText(R.string.cancel))
        cancelButton.perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withText(R.string.confirm_pass_change)).check(ViewAssertions.doesNotExist())
    }

    @Test
    fun forgotDialogOpensInputCodeActivity() {
        Intents.init()
        Espresso.onView(ViewMatchers.withId(R.id.et_email))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(replaceText("test@email.com"))
        SystemClock.sleep(100)
        Espresso.onView(ViewMatchers.withId(R.id.et_pass))
                .perform(setFocus())

        val btForgot = Espresso.onView(ViewMatchers.withId(R.id.bt_forgot))
        btForgot.check(matches(ViewMatchers.isDisplayed()))
        btForgot.perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withText(R.string.confirm_pass_change)).check(matches(ViewMatchers.isDisplayed()))

        val confirmButton = Espresso.onView(ViewMatchers.withText(R.string.confirm))
        confirmButton.perform(ViewActions.click())

        Intents.intended(IntentMatchers.hasComponent(ChangePassActivity::class.java.name))
        Intents.release()
    }

}
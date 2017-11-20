package com.paradigmadigital.ui.register

import android.os.SystemClock
import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.matcher.IntentMatchers
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.paradigmadigital.R
import com.paradigmadigital.ui.inputcode.InputCodeActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RegisterActivityInstrumentedTest {

    @get:Rule
    var activityTestRule = ActivityTestRule(RegisterActivity::class.java)


    @Test
    fun goToInputCodeActivityOnRegisterClickWithCorrectData() {
        Intents.init()

        SystemClock.sleep(100)
        Espresso.onView(ViewMatchers.withId(R.id.et_name))
                .perform(ViewActions.closeSoftKeyboard())
                .perform(ViewActions.replaceText("Pepe"))
        SystemClock.sleep(100)
        Espresso.onView(ViewMatchers.withId(R.id.et_tel))
                .perform(ViewActions.replaceText("123456789"))
        SystemClock.sleep(100)
        Espresso.onView(ViewMatchers.withId(R.id.et_email1))
                .perform(ViewActions.replaceText("test@email.com"))
        SystemClock.sleep(100)
        Espresso.onView(ViewMatchers.withId(R.id.et_email2))
                .perform(ViewActions.replaceText("test@email.com"))
        SystemClock.sleep(100)
        Espresso.onView(ViewMatchers.withId(R.id.et_pass1))
                .perform(ViewActions.replaceText("12345"))
        SystemClock.sleep(100)
        Espresso.onView(ViewMatchers.withId(R.id.et_pass2))
                .perform(ViewActions.replaceText("12345"))
        SystemClock.sleep(100)
        Espresso.onView(ViewMatchers.withId(R.id.bt_register))
               .perform(ViewActions.click())

        SystemClock.sleep(1500)
        Intents.intended(IntentMatchers.hasComponent(InputCodeActivity::class.java.name))
        Intents.release()
    }


    @Test
    fun showErrorsOnRegisterClickWithEmptyData() {
        SystemClock.sleep(100)
         Espresso.onView(ViewMatchers.withId(R.id.et_email1))
                .perform(ViewActions.closeSoftKeyboard())
                .perform(ViewActions.replaceText(""))
        SystemClock.sleep(100)
        Espresso.onView(ViewMatchers.withId(R.id.et_email2))
                .perform(ViewActions.replaceText(""))
        SystemClock.sleep(100)
        Espresso.onView(ViewMatchers.withId(R.id.et_pass1))
                .perform(ViewActions.replaceText(""))
        SystemClock.sleep(100)
        Espresso.onView(ViewMatchers.withId(R.id.et_pass2))
                .perform(ViewActions.replaceText(""))
        SystemClock.sleep(100)
        Espresso.onView(ViewMatchers.withId(R.id.bt_register))
                .perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.et_email1))
                .check(ViewAssertions.matches(ViewMatchers.hasErrorText("Invalid email format")))
        Espresso.onView(ViewMatchers.withId(R.id.et_email2))
                .check(ViewAssertions.matches(ViewMatchers.hasErrorText("Invalid email format")))
        Espresso.onView(ViewMatchers.withId(R.id.et_pass1))
                .check(ViewAssertions.matches(ViewMatchers.hasErrorText("Password cannot be empty")))
        Espresso.onView(ViewMatchers.withId(R.id.et_pass2))
                .check(ViewAssertions.matches(ViewMatchers.hasErrorText("Password cannot be empty")))
    }

    @Test
    fun showErrorsOnRegisterClickWithDifferentData() {
        SystemClock.sleep(100)
        Espresso.onView(ViewMatchers.withId(R.id.et_email1))
                .perform(ViewActions.closeSoftKeyboard())
                .perform(ViewActions.replaceText("test@email.com"))
        SystemClock.sleep(100)
        Espresso.onView(ViewMatchers.withId(R.id.et_email2))
                .perform(ViewActions.replaceText(""))
        SystemClock.sleep(100)
        Espresso.onView(ViewMatchers.withId(R.id.et_pass1))
                .perform(ViewActions.replaceText("12345"))
        SystemClock.sleep(100)
        Espresso.onView(ViewMatchers.withId(R.id.et_pass2))
                .perform(ViewActions.replaceText(""))
        SystemClock.sleep(100)
        Espresso.onView(ViewMatchers.withId(R.id.bt_register))
                .perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.et_email2))
                .check(ViewAssertions.matches(ViewMatchers.hasErrorText("Email address don\'t match")))
        Espresso.onView(ViewMatchers.withId(R.id.et_pass2))
                .check(ViewAssertions.matches(ViewMatchers.hasErrorText("Passwords don\'t match")))
    }

}
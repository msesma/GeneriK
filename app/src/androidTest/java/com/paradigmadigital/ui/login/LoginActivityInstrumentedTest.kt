package com.paradigmadigital.ui.login

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
import com.paradigmadigital.postoscarrefour.R
import com.paradigmadigital.ui.inputcode.InputCodeActivity
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
        activityTestRule.activity
        Intents.init()

        Espresso.onView(ViewMatchers.withId(R.id.et_email))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(replaceText("test@email.com"))
        Espresso.onView(ViewMatchers.withId(R.id.et_pass))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(replaceText("12345"))
        Espresso.onView(ViewMatchers.withId(R.id.bt_login))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(ViewActions.click())

        Intents.intended(IntentMatchers.hasComponent(MainActivity::class.java.name))
        //TODO Verify data is inserted in repository
        Intents.release()
    }

    @Test
    fun showErrorOnLoginClickWithBadMail() {
        activityTestRule.activity

        Espresso.onView(ViewMatchers.withId(R.id.et_email))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(replaceText("test@email"))
        Espresso.onView(ViewMatchers.withId(R.id.et_pass))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(replaceText("12345"))
        Espresso.onView(ViewMatchers.withId(R.id.bt_login))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.et_email))
                .check(matches(hasErrorText("Invalid email format")))
    }

    @Test
    fun showErrorOnLoginClickWithNoMail() {
        activityTestRule.activity

        Espresso.onView(ViewMatchers.withId(R.id.et_email))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(replaceText(""))
        Espresso.onView(ViewMatchers.withId(R.id.et_pass))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(replaceText("12345"))
        Espresso.onView(ViewMatchers.withId(R.id.bt_login))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.et_email))
                .check(matches(hasErrorText("Invalid email format")))
    }

    @Test
    fun showErrorOnLoginClickWithNoPass() {
        activityTestRule.activity

        Espresso.onView(ViewMatchers.withId(R.id.et_email))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(replaceText("test@email.com"))
        Espresso.onView(ViewMatchers.withId(R.id.et_pass))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(replaceText(""))
        Espresso.onView(ViewMatchers.withId(R.id.bt_login))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.et_pass))
                .check(matches(hasErrorText("Password cannot be empty")))
    }

    @Test
    fun forgotDialogOpensAndCloses() {
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
        activityTestRule.activity
        Intents.init()
        val btForgot = Espresso.onView(ViewMatchers.withId(R.id.bt_forgot))

        btForgot.check(matches(ViewMatchers.isDisplayed()))
        btForgot.perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withText(R.string.confirm_pass_change)).check(matches(ViewMatchers.isDisplayed()))

        val confirmButton = Espresso.onView(ViewMatchers.withText(R.string.confirm))
        confirmButton.perform(ViewActions.click())

        Intents.intended(IntentMatchers.hasComponent(InputCodeActivity::class.java.name))
        Intents.release()
    }

}
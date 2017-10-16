package com.paradigmadigital.ui.register

import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.matcher.IntentMatchers
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.paradigmadigital.R
import com.paradigmadigital.ui.loginregister.LoginRegisterActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RegisterActivityInstrumentedTest {

    @get:Rule
    var activityTestRule = ActivityTestRule(RegisterActivity::class.java)


    @Test
    fun goToLoginRegisterOnRegisterClickWithCorrectData() {
        activityTestRule.activity
        Intents.init()

        Espresso.onView(ViewMatchers.withId(R.id.et_name))
                .perform(ViewActions.closeSoftKeyboard())
                .perform(ViewActions.replaceText("Pepe"))
        Espresso.onView(ViewMatchers.withId(R.id.et_tel))
                .perform(ViewActions.scrollTo())
                .perform(ViewActions.replaceText("123456789"))
        Espresso.onView(ViewMatchers.withId(R.id.et_email1))
                .perform(ViewActions.scrollTo())
                .perform(ViewActions.replaceText("test@email.com"))
        Espresso.onView(ViewMatchers.withId(R.id.et_email2))
                .perform(ViewActions.scrollTo())
                .perform(ViewActions.replaceText("test@email.com"))
        Espresso.onView(ViewMatchers.withId(R.id.et_pass1))
                .perform(ViewActions.scrollTo())
                .perform(ViewActions.replaceText("12345"))
        Espresso.onView(ViewMatchers.withId(R.id.et_pass2))
                .perform(ViewActions.scrollTo())
                .perform(ViewActions.replaceText("12345"))
        Espresso.onView(ViewMatchers.withId(R.id.bt_register))
                .perform(ViewActions.scrollTo())
                .perform(ViewActions.click())

        Intents.intended(IntentMatchers.hasComponent(LoginRegisterActivity::class.java.name))
        //TODO Verify data is inserted in repository
        Intents.release()
    }


    @Test
    fun showErrorsOnRegisterClickWithEmptyData() {
        activityTestRule.activity

        Espresso.onView(ViewMatchers.withId(R.id.et_email1))
                .perform(ViewActions.closeSoftKeyboard())
                .perform(ViewActions.scrollTo())
                .perform(ViewActions.replaceText(""))
        Espresso.onView(ViewMatchers.withId(R.id.et_email2))
                .perform(ViewActions.scrollTo())
                .perform(ViewActions.replaceText(""))
        Espresso.onView(ViewMatchers.withId(R.id.et_pass1))
                .perform(ViewActions.scrollTo())
                .perform(ViewActions.replaceText(""))
        Espresso.onView(ViewMatchers.withId(R.id.et_pass2))
                .perform(ViewActions.scrollTo())
                .perform(ViewActions.replaceText(""))
        Espresso.onView(ViewMatchers.withId(R.id.bt_register))
                .perform(ViewActions.scrollTo())
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
        activityTestRule.activity

        Espresso.onView(ViewMatchers.withId(R.id.et_email1))
                .perform(ViewActions.closeSoftKeyboard())
                .perform(ViewActions.scrollTo())
                .perform(ViewActions.replaceText("test@email.com"))
        Espresso.onView(ViewMatchers.withId(R.id.et_email2))
                .perform(ViewActions.scrollTo())
                .perform(ViewActions.replaceText(""))
        Espresso.onView(ViewMatchers.withId(R.id.et_pass1))
                .perform(ViewActions.scrollTo())
                .perform(ViewActions.replaceText("12345"))
        Espresso.onView(ViewMatchers.withId(R.id.et_pass2))
                .perform(ViewActions.scrollTo())
                .perform(ViewActions.replaceText(""))
        Espresso.onView(ViewMatchers.withId(R.id.bt_register))
                .perform(ViewActions.scrollTo())
                .perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.et_email2))
                .check(ViewAssertions.matches(ViewMatchers.hasErrorText("Email address don\'t match")))
        Espresso.onView(ViewMatchers.withId(R.id.et_pass2))
                .check(ViewAssertions.matches(ViewMatchers.hasErrorText("Passwords don\'t match")))
    }

}
package com.paradigmadigital.ui.changepass

import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.paradigmadigital.R
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ChangePassActivityInstrumentedTest {

    @get:Rule
    var activityTestRule = ActivityTestRule(ChangePassActivity::class.java)

    @Test
    fun goToLoginOnEnterClickWithCorrectData() {
        activityTestRule.activity

        Espresso.onView(ViewMatchers.withId(R.id.et_pass1))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(ViewActions.replaceText("12345"))
        Espresso.onView(ViewMatchers.withId(R.id.et_pass2))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(ViewActions.replaceText("12345"))
        Espresso.onView(ViewMatchers.withId(R.id.bt_enter))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(ViewActions.click())

        Assert.assertTrue(activityTestRule.activity.isFinishing)
    }

    @Test
    fun showErrorsOnRegisterClickWithEmptyDataOnPass1() {
        activityTestRule.activity

        Espresso.onView(ViewMatchers.withId(R.id.et_pass1))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(ViewActions.replaceText(""))
        Espresso.onView(ViewMatchers.withId(R.id.et_pass2))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(ViewActions.replaceText(""))
        Espresso.onView(ViewMatchers.withId(R.id.bt_enter))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.et_pass1))
                .check(ViewAssertions.matches(ViewMatchers.hasErrorText("Password cannot be empty")))
    }

    @Test
    fun showErrorsOnRegisterClickWithEmptyDataOnPass2() {
        activityTestRule.activity

        Espresso.onView(ViewMatchers.withId(R.id.et_pass1))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(ViewActions.replaceText("12345"))
        Espresso.onView(ViewMatchers.withId(R.id.et_pass2))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(ViewActions.replaceText(""))
        Espresso.onView(ViewMatchers.withId(R.id.bt_enter))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.et_pass2))
                .check(ViewAssertions.matches(ViewMatchers.hasErrorText("Password cannot be empty")))
    }

    @Test
    fun showErrorsOnRegisterClickWithDifferentData() {
        activityTestRule.activity

        Espresso.onView(ViewMatchers.withId(R.id.et_pass1))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(ViewActions.replaceText("12345"))
        Espresso.onView(ViewMatchers.withId(R.id.et_pass2))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(ViewActions.replaceText("54321"))
        Espresso.onView(ViewMatchers.withId(R.id.bt_enter))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.et_pass2))
                .check(ViewAssertions.matches(ViewMatchers.hasErrorText("Passwords don't match")))
    }

}
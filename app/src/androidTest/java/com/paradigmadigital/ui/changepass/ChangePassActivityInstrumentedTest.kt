package com.paradigmadigital.ui.changepass

import android.content.Intent
import android.os.SystemClock
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.paradigmadigital.R
import com.paradigmadigital.navigation.Navigator
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ChangePassActivityInstrumentedTest {


    @get: Rule
    val activityTestRule = object : ActivityTestRule<ChangePassActivity>(ChangePassActivity::class.java) {
        override fun getActivityIntent(): Intent {
            val targetContext = InstrumentationRegistry.getInstrumentation().targetContext
            return Intent(targetContext, ChangePassActivity::class.java)
                    .putExtra(Navigator.EXTRA_EMAIL, "bob@acme.com")
        }
    }

    @Test
    fun goToLoginOnEnterClickWithCorrectData() {

        SystemClock.sleep(100)
        Espresso.onView(ViewMatchers.withId(R.id.et_pass1))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(ViewActions.replaceText("12345"))
        SystemClock.sleep(100)
        Espresso.onView(ViewMatchers.withId(R.id.et_pass2))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(ViewActions.replaceText("12345"))
        SystemClock.sleep(100)
        Espresso.onView(ViewMatchers.withId(R.id.bt_enter))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(ViewActions.click())

        Assert.assertTrue(activityTestRule.activity.isFinishing)
    }

    @Test
    fun showErrorsOnRegisterClickWithEmptyDataOnPass1() {

        SystemClock.sleep(100)
        Espresso.onView(ViewMatchers.withId(R.id.et_pass1))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(ViewActions.replaceText(""))
        SystemClock.sleep(100)
        Espresso.onView(ViewMatchers.withId(R.id.et_pass2))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(ViewActions.replaceText(""))
        SystemClock.sleep(100)
        Espresso.onView(ViewMatchers.withId(R.id.bt_enter))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.et_pass1))
                .check(ViewAssertions.matches(ViewMatchers.hasErrorText("Password cannot be empty")))
    }

    @Test
    fun showErrorsOnRegisterClickWithEmptyDataOnPass2() {

        SystemClock.sleep(100)
        Espresso.onView(ViewMatchers.withId(R.id.et_pass1))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(ViewActions.replaceText("12345"))
        SystemClock.sleep(100)
        Espresso.onView(ViewMatchers.withId(R.id.et_pass2))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(ViewActions.replaceText(""))
        SystemClock.sleep(100)
        Espresso.onView(ViewMatchers.withId(R.id.bt_enter))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.et_pass2))
                .check(ViewAssertions.matches(ViewMatchers.hasErrorText("Password cannot be empty")))
    }

    @Test
    fun showErrorsOnRegisterClickWithDifferentData() {

        SystemClock.sleep(100)
        Espresso.onView(ViewMatchers.withId(R.id.et_pass1))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(ViewActions.replaceText("12345"))
        SystemClock.sleep(100)
        Espresso.onView(ViewMatchers.withId(R.id.et_pass2))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(ViewActions.replaceText("54321"))
        SystemClock.sleep(100)
        Espresso.onView(ViewMatchers.withId(R.id.bt_enter))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.et_pass2))
                .check(ViewAssertions.matches(ViewMatchers.hasErrorText("Passwords don't match")))
    }

}
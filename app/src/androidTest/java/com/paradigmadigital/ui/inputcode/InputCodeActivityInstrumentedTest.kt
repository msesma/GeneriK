package com.paradigmadigital.ui.inputcode

import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.matcher.IntentMatchers
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.paradigmadigital.postoscarrefour.R
import com.paradigmadigital.ui.changepass.ChangePassActivity
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class InputCodeActivityInstrumentedTest {

    @get:Rule
    var activityTestRule = ActivityTestRule(InputCodeActivity::class.java)


    @Test
    fun check0to4Buttons() {
        activityTestRule.activity

        Espresso.onView(ViewMatchers.withId(R.id.bt_0))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.bt_1))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.bt_2))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.bt_3))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.bt_4))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(ViewActions.click())

        Espresso.onView(Matchers.allOf(ViewMatchers.withId(R.id.tv_code), ViewMatchers.withText("0 1 2 3 4 ")))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun check5to9Buttons() {
        activityTestRule.activity

        Espresso.onView(ViewMatchers.withId(R.id.bt_5))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.bt_6))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.bt_7))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.bt_8))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.bt_9))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(ViewActions.click())

        Espresso.onView(Matchers.allOf(ViewMatchers.withId(R.id.tv_code), ViewMatchers.withText("5 6 7 8 9 ")))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun checkBackButton() {
        activityTestRule.activity

        Espresso.onView(ViewMatchers.withId(R.id.bt_back))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.bt_5))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.bt_6))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.bt_back))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.bt_8))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.bt_9))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.bt_back))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(ViewActions.click())

        Espresso.onView(Matchers.allOf(ViewMatchers.withId(R.id.tv_code), ViewMatchers.withText("5 8 ")))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }


    @Test
    fun goChangePaswordOn6DigitsClickWithCorrectData() {
        activityTestRule.activity
        Intents.init()

        Espresso.onView(ViewMatchers.withId(R.id.bt_0))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.bt_1))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.bt_2))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.bt_3))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.bt_4))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.bt_5))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(ViewActions.click())


        Intents.intended(IntentMatchers.hasComponent(ChangePassActivity::class.java.name))
        Intents.release()
    }

    @Test
    fun goChangePaswordOnSMSWithCorrectData() {
        activityTestRule.activity
        Intents.init()

        Espresso.onView(ViewMatchers.withId(R.id.bt_new))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(ViewActions.click())

        Intents.intended(IntentMatchers.hasComponent(ChangePassActivity::class.java.name))
        Intents.release()
    }
}
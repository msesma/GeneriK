package com.paradigmadigital.ui.inputcode


import android.arch.persistence.room.Room
import android.os.SystemClock
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.matcher.IntentMatchers
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.paradigmadigital.R
import com.paradigmadigital.domain.db.Database
import com.paradigmadigital.domain.entities.User
import com.paradigmadigital.ui.loginregister.LoginRegisterActivity
import org.hamcrest.Matchers
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class InputCodeActivityInstrumentedTest {

    @get:Rule
    var activityTestRule = ActivityTestRule(InputCodeActivity::class.java)

    companion object {
        @JvmStatic
        @BeforeClass
        fun ClassSetUp() {
            val context = InstrumentationRegistry.getTargetContext()
            val db = Room.databaseBuilder(context, Database::class.java, "data.db")
                    .allowMainThreadQueries()
                    .build()
            val userDao = db.userDao()
            userDao.insert(User(email = "bob@acme.com"))
        }
    }


    @Test
    fun check0to4Buttons() {

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


//    @Test
//    fun goLoginregisterOnSMSWithCorrectData() {
//        Intents.init()
//
//        Espresso.onView(ViewMatchers.withId(R.id.bt_new))
//                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
//                .perform(ViewActions.click())
//
//        SystemClock.sleep(3500)
//        Intents.intended(IntentMatchers.hasComponent(LoginRegisterActivity::class.java.name))
//        Intents.release()
//    }

    @Test
    fun goLoginregisterOn6DigitsClickWithCorrectData() {
        Intents.init()

        Espresso.onView(ViewMatchers.withId(R.id.bt_6))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.bt_5))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.bt_4))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.bt_3))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.bt_2))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.bt_1))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                .perform(ViewActions.click())

        SystemClock.sleep(23500)
        Intents.intended(IntentMatchers.hasComponent(LoginRegisterActivity::class.java.name))
        Intents.release()
    }

}
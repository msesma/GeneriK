package com.paradigmadigital.navigation

import android.app.Activity
import android.app.Instrumentation
import android.arch.persistence.room.Room
import android.net.Uri
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
import com.paradigmadigital.domain.db.UserDao
import com.paradigmadigital.domain.entities.User
import com.paradigmadigital.ui.loginregister.LoginRegisterActivity
import com.paradigmadigital.ui.main.MainActivity
import org.hamcrest.Matchers
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DrawerInstrumentedTest {


    companion object {
        private val TERMS_URL = "https://www.paradigmadigital.com/quienes-somos/"
        private val WAIT_DRAWER_OPEN_MILLIS = 500L
        private lateinit var userDao: UserDao
        @JvmStatic
        @BeforeClass
        fun ClassSetUp() {
            val context = InstrumentationRegistry.getTargetContext()
            val db = Room.databaseBuilder(context, Database::class.java, "data.db")
                    .allowMainThreadQueries()
                    .build()
            userDao = db.userDao()
            userDao.insert(User(email = "bob@acme.com", name = "Bob"))
        }
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
    fun userNameAndEmailInDrawerHeader() {
        activityTestRule.activity
        Espresso.onView(ViewMatchers.withContentDescription("Open"))
                .perform(ViewActions.click())
        SystemClock.sleep(WAIT_DRAWER_OPEN_MILLIS)

        Espresso.onView(ViewMatchers.withText("bob@acme.com"))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withText("Bob"))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun startTermsWebViewOnTermsClick() {
        activityTestRule.activity
        Intents.init()
        Intents.intending(Matchers.not(IntentMatchers.isInternal()))
                .respondWith(Instrumentation.ActivityResult(Activity.RESULT_OK, null))

        Espresso.onView(ViewMatchers.withContentDescription("Open"))
                .perform(ViewActions.click())
        SystemClock.sleep(WAIT_DRAWER_OPEN_MILLIS)
        Espresso.onView(ViewMatchers.withText(R.string.terms))
                .perform(ViewActions.click())

        SystemClock.sleep(100)
        Intents.intended(IntentMatchers.hasData(Uri.parse(DrawerInstrumentedTest.TERMS_URL)))
        Intents.release()
    }

    @Test
    fun startLoginRegisterWebViewOnLogoutClick() {
        activityTestRule.activity
        Intents.init()
        Intents.intending(Matchers.not(IntentMatchers.isInternal()))
                .respondWith(Instrumentation.ActivityResult(Activity.RESULT_OK, null))

        Espresso.onView(ViewMatchers.withContentDescription("Open"))
                .perform(ViewActions.click())
        SystemClock.sleep(WAIT_DRAWER_OPEN_MILLIS)
        Espresso.onView(ViewMatchers.withText(R.string.logout))
                .perform(ViewActions.click())

        SystemClock.sleep(100)
        Intents.intended(IntentMatchers.hasComponent(LoginRegisterActivity::class.java.name))
        Intents.release()
    }
}
package com.paradigmadigital.ui.splash

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.paradigmadigital.R
import com.paradigmadigital.domain.db.Database
import com.paradigmadigital.domain.db.UserDao
import com.paradigmadigital.domain.entities.User
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SplashActivityInstrumentedTest {

    companion object {
        private lateinit var userDao: UserDao
        @JvmStatic
        @BeforeClass
        fun ClassSetUp() {
            val context = InstrumentationRegistry.getTargetContext()
            val db = Room.databaseBuilder(context, Database::class.java, "data.db")
                    .allowMainThreadQueries()
                    .build()
            userDao = db.userDao()
            userDao.insert(User(email = "bob@acme.com"))
        }
    }

    @get:Rule
    var activityTestRule = ActivityTestRule(SplashActivity::class.java, true, false)

//    @Test
//    fun goToLoginRegisterIfNotLoggedIn() {
//        userDao.insert(User(token = ""))
//        activityTestRule.launchActivity(null)
//        Intents.init()
//
//        SystemClock.sleep(500)
//        Intents.intended(IntentMatchers.hasComponent(LoginRegisterActivity::class.java.name))
//        Intents.release()
//    }
//
//    @Test
//    fun goToMainIfLoggedIn() {
//        userDao.insert(User(token = "hljshfLJEHFljehflsJDHFLJKSD"))
//        activityTestRule.launchActivity(null)
//        Intents.init()
//
//
//        SystemClock.sleep(500)
//        Intents.intended(IntentMatchers.hasComponent(MainActivity::class.java.name))
//        Intents.release()
//    }

    @Test
    fun ShowSplashScreen() {
        activityTestRule.launchActivity(null)

        Espresso.onView(ViewMatchers.withId(R.id.logo))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

}
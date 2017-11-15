package com.paradigmadigital.ui.main

import android.support.test.espresso.Espresso
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.BoundedMatcher
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.Toolbar
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainActivityInstrumentedTest {

    @get:Rule
    var activityTestRule = ActivityTestRule(MainActivity::class.java)

    //    companion object {
//        private lateinit var userDao: UserDao
//        @JvmStatic
//        @BeforeClass
//        fun ClassSetUp() {
//            val context = InstrumentationRegistry.getTargetContext()
//            val db = Room.databaseBuilder(context, Database::class.java, "data.db")
//                    .allowMainThreadQueries()
//                    .build()
//            userDao = db.userDao()
//            userDao.insert(User(email = "bob@acme.com"))
//        }
//    }


    @Test
    @Ignore
    fun greetUser() {
        activityTestRule.activity

        Espresso.onView(ViewMatchers.isAssignableFrom(Toolbar::class.java))
                .check(ViewAssertions.matches(withToolbarTitle(Matchers.containsString("Hi"))))

    }

    private fun withToolbarTitle(textMatcher: Matcher<String>): Matcher<Any> {
        return object : BoundedMatcher<Any, Toolbar>(Toolbar::class.java) {
            public override fun matchesSafely(toolbar: Toolbar): Boolean {
                return textMatcher.matches(toolbar.title)
            }

            override fun describeTo(description: Description) {
                description.appendText("with toolbar title: ")
                textMatcher.describeTo(description)
            }
        }
    }
}
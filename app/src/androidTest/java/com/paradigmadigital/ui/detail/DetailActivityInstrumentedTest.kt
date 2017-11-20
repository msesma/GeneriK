package com.paradigmadigital.ui.detail

import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.BoundedMatcher
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.Toolbar
import com.paradigmadigital.R
import com.paradigmadigital.domain.entities.PostUiModel
import com.paradigmadigital.navigation.Navigator
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class DetailActivityInstrumentedTest {

    @get:Rule
    var activityTestRule: ActivityTestRule<DetailActivity> = object : ActivityTestRule<DetailActivity>(DetailActivity::class.java) {
        override fun getActivityIntent(): Intent {
            val targetContext = InstrumentationRegistry.getInstrumentation().targetContext
            val result = Intent(targetContext, DetailActivity::class.java)
            val post = PostUiModel(
                    id = 1,
                    title = "tittle",
                    body = "body",
                    name = "name",
                    email = "email"
            )
            result.putExtra(Navigator.EXTRA_ITEM, post)
            return result
        }
    }

    @Test
    fun showTimeOnToolbar() {
        val toolbarTitle: CharSequence = "tittle"
        Espresso.onView(isAssignableFrom(Toolbar::class.java))
                .check(matches(withToolbarTitle(`is`(toolbarTitle))))
    }

    @Test
    fun showDetailData() {
        Espresso.onView(withId(R.id.title))
                .check(matches(withText("tittle")))
        Espresso.onView(withId(R.id.body))
                .check(matches(withText("body")))
        Espresso.onView(withId(R.id.author))
                .check(matches(withText("name")))
    }

    private fun withToolbarTitle(textMatcher: Matcher<CharSequence>): Matcher<Any> {
        return object : BoundedMatcher<Any, Toolbar>(Toolbar::class.java) {
            override fun describeTo(description: org.hamcrest.Description) {
                description.appendText("with toolbar title: ")
                textMatcher.describeTo(description)
            }

            override fun matchesSafely(toolbar: Toolbar): Boolean {
                return textMatcher.matches(toolbar.title)
            }
        }
    }
}

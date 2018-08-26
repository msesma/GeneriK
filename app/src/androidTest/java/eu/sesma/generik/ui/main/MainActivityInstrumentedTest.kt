package eu.sesma.generik.ui.main

import android.arch.persistence.room.Room
import android.os.SystemClock
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.matcher.IntentMatchers
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import eu.sesma.generik.R
import eu.sesma.generik.domain.db.Database
import eu.sesma.generik.domain.db.UserDao
import eu.sesma.generik.domain.entities.User
import eu.sesma.generik.ui.detail.DetailActivity
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainActivityInstrumentedTest {

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
            userDao.insert(User(email = "bob@acme.com", name = "Bob"))
        }
    }

    @get:Rule
    var activityTestRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @Test
    fun startDetailActivityOnItemClick() {
        Intents.init()

        SystemClock.sleep(1500)
        Espresso.onView(ViewMatchers.withId(R.id.main_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, ViewActions.click()))

        SystemClock.sleep(1500)
        Intents.intended(IntentMatchers.hasComponent(DetailActivity::class.java.name))
        Intents.release()
    }
}
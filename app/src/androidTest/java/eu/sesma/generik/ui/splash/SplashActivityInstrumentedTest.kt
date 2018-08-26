package eu.sesma.generik.ui.splash

import android.arch.persistence.room.Room
import android.os.SystemClock
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.matcher.IntentMatchers
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import eu.sesma.generik.R
import eu.sesma.generik.account.OauthAccountManager
import eu.sesma.generik.api.model.Login
import eu.sesma.generik.domain.db.Database
import eu.sesma.generik.domain.db.UserDao
import eu.sesma.generik.domain.entities.User
import eu.sesma.generik.ui.loginregister.LoginRegisterActivity
import eu.sesma.generik.ui.main.MainActivity
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SplashActivityInstrumentedTest {

    @get:Rule
    var activityTestRule = ActivityTestRule(SplashActivity::class.java, true, false)

    val accountManager: OauthAccountManager = OauthAccountManager(InstrumentationRegistry.getTargetContext())

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

    @Test
    fun goToLoginRegisterIfNotLoggedIn() {
        accountManager.addAccount(Login(token = ""))
        activityTestRule.launchActivity(null)
        Intents.init()

        SystemClock.sleep(500)
        Intents.intended(IntentMatchers.hasComponent(LoginRegisterActivity::class.java.name))
        Intents.release()
    }

    @Test
    fun goToMainIfLoggedIn() {
        accountManager.addAccount(Login(token = "hljshfLJEHFljehflsJDHFLJKSD"))
        activityTestRule.launchActivity(null)
        Intents.init()


        SystemClock.sleep(500)
        Intents.intended(IntentMatchers.hasComponent(MainActivity::class.java.name))
        Intents.release()
    }

    @Test
    fun ShowSplashScreen() {
        activityTestRule.launchActivity(null)

        Espresso.onView(ViewMatchers.withId(R.id.logo))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

}
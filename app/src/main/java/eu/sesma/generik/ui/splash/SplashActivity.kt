package eu.sesma.generik.ui.splash

import android.os.Bundle
import android.os.Handler
import eu.sesma.generik.R
import eu.sesma.generik.navigation.Navigator
import eu.sesma.generik.repository.LoginRepository
import eu.sesma.generik.ui.BaseActivity
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SplashActivity : BaseActivity() {

    companion object {
        private val DELAY = TimeUnit.MILLISECONDS.toMillis(250)
    }

    @Inject
    lateinit var navigator: Navigator
    @Inject
    lateinit var repository: LoginRepository


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        activityComponent.inject(this)

        Handler().postDelayed(Runnable(this::navigate), DELAY)
    }

    private fun navigate() {
        logoutOnDataLoss()

        if (!repository.isLoggedIn()) {
            navigator.navigateToLoginRegister()
            finish()
            return
        }

        if (repository.requirePin()) navigator.navigateToPin() else navigator.navigateToMain()
        finish()
    }

    private fun logoutOnDataLoss() {
        if (repository.getUser() == null && repository.isLoggedIn()) repository.localLogout()
    }
}
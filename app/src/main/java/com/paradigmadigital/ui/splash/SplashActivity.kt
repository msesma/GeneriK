package com.paradigmadigital.ui.splash

import android.os.Bundle
import android.os.Handler
import com.paradigmadigital.R
import com.paradigmadigital.navigation.Navigator
import com.paradigmadigital.repository.LoginRepository
import com.paradigmadigital.ui.BaseActivity
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
        if (repository.isLoggedIn()) navigator.navigateToMain() else navigator.navigateToLoginRegister()
        finish()
    }
}
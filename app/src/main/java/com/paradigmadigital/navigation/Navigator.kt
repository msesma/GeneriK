package com.paradigmadigital.navigation

import android.content.Intent
import com.paradigmadigital.repository.preferences.SettingsActivity
import com.paradigmadigital.ui.BaseActivity
import com.paradigmadigital.ui.changepass.ChangePassActivity
import com.paradigmadigital.ui.inputcode.InputCodeActivity
import com.paradigmadigital.ui.login.LoginActivity
import com.paradigmadigital.ui.loginregister.LoginRegisterActivity
import com.paradigmadigital.ui.main.MainActivity
import com.paradigmadigital.ui.register.RegisterActivity
import com.paradigmadigital.ui.terms.CustomTabsManager
import javax.inject.Inject


class Navigator
@Inject
constructor(
        private val activity: BaseActivity,
        private val browser: CustomTabsManager
) {
    companion object {
        private val TERMS_URL = "https://www.paradigmadigital.com/quienes-somos/"
    }

    fun closeActivity() {
        activity.finish()
    }

    fun navigateToLoginRegister() {
        val intent = Intent(activity, LoginRegisterActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        activity.startActivity(intent)
    }

    fun navigateToMain() {
        val intent = Intent(activity, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        activity.startActivity(intent)
    }

    fun navigateToLogin() {
        val intent = Intent(activity, LoginActivity::class.java)
        activity.startActivity(intent)
    }

    fun navigateToRegister() {
        val intent = Intent(activity, RegisterActivity::class.java)
        activity.startActivity(intent)
    }

    fun navigateToInputCode() {
        val intent = Intent(activity, InputCodeActivity::class.java)
        activity.startActivity(intent)
    }

    fun navigateToTerms() {
        browser.showContent(TERMS_URL)
    }

    fun navigateToChangePassword() {
        val intent = Intent(activity, ChangePassActivity::class.java)
        activity.startActivity(intent)
    }

    fun navigateToSettings() {
        val intent = Intent(activity, SettingsActivity::class.java)
        activity.startActivity(intent)
    }

}
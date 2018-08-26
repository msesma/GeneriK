package eu.sesma.generik.navigation

import android.content.Intent
import eu.sesma.generik.domain.entities.PostUiModel
import eu.sesma.generik.repository.preferences.SettingsActivity
import eu.sesma.generik.ui.BaseActivity
import eu.sesma.generik.ui.changepass.ChangePassActivity
import eu.sesma.generik.ui.detail.DetailActivity
import eu.sesma.generik.ui.inputcode.InputCodeActivity
import eu.sesma.generik.ui.login.LoginActivity
import eu.sesma.generik.ui.loginregister.LoginRegisterActivity
import eu.sesma.generik.ui.main.MainActivity
import eu.sesma.generik.ui.pin.PinActivity
import eu.sesma.generik.ui.profile.ProfileActivity
import eu.sesma.generik.ui.register.RegisterActivity
import eu.sesma.generik.ui.terms.CustomTabsManager
import javax.inject.Inject


class Navigator
@Inject
constructor(
        private val activity: BaseActivity,
        private val browser: CustomTabsManager
) {
    companion object {
        private val TERMS_URL = "https://www.paradigmadigital.com/quienes-somos/"
        val EXTRA_EMAIL = "extra_email"
        val EXTRA_PASS = "extra_pass"
        val EXTRA_ITEM = "extra_item"
    }

    fun closeActivity() {
        activity.finish()
    }

    fun navigateToPin() {
        val intent = Intent(activity, PinActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        activity.startActivity(intent)
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

    fun navigateToProfile() {
        val intent = Intent(activity, ProfileActivity::class.java)
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

    fun navigateToInputCode(email: String, pass: String) {
        val intent = Intent(activity, InputCodeActivity::class.java)
        intent.putExtra(EXTRA_EMAIL, email)
        intent.putExtra(EXTRA_PASS, pass)
        activity.startActivity(intent)
    }

    fun navigateToTerms() {
        browser.showContent(TERMS_URL)
    }

    fun navigateToChangePassword(email: String) {
        val intent = Intent(activity, ChangePassActivity::class.java)
        intent.putExtra(EXTRA_EMAIL, email)
        activity.startActivity(intent)
    }

    fun navigateToSettings() {
        val intent = Intent(activity, SettingsActivity::class.java)
        activity.startActivity(intent)
    }

    fun navigateToDetail(post: PostUiModel) {
        val intent = Intent(activity, DetailActivity::class.java)
        intent.putExtra(EXTRA_ITEM, post)
        activity.startActivity(intent)
    }


}
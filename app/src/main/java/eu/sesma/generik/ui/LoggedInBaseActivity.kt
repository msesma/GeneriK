package eu.sesma.generik.ui

import eu.sesma.generik.navigation.Navigator
import eu.sesma.generik.repository.preferences.Preferences
import javax.inject.Inject


abstract class LoggedInBaseActivity : BaseActivity() {

    @Inject
    lateinit var preferences: Preferences
    @Inject
    lateinit var navigator: Navigator

    override fun onResume() {
        super.onResume()

        if (preferences.requirePin && preferences.timeout) {
            navigator.navigateToPin()
            navigator.closeActivity()
            return
        }

        preferences.timeout = true
    }

}
package eu.sesma.generik.ui.pin

import com.github.ajalt.reprint.core.AuthenticationResult.Status.SUCCESS
import eu.sesma.generik.navigation.Navigator
import eu.sesma.generik.repository.preferences.Preferences
import io.reactivex.disposables.Disposable
import javax.inject.Inject


class PinPresenter
@Inject
constructor(
        private val navigator: Navigator,
        private val fingerprintManager: FingerprintManager,
        private val preferences: Preferences
) {

    private var decorator: PinUserInterface? = null
    private var disposable: Disposable? = null

    private val delegate = object : PinUserInterface.Delegate {
        override fun onCode(pin: String) {
           if (pin == preferences.pin) {
                preferences.timeout = true
                navigator.navigateToMain()
            }
        }
    }

    fun initialize(decorator: PinUserInterface) {
        this.decorator = decorator
        this.decorator?.initialize(delegate)

        //We will only consider the happy case. Any other result will hide the fingerprint icon
        if (preferences.allowFingerPrint) {
            this.decorator?.showFingerprint(true)
            disposable = fingerprintManager.startAuth().subscribe({
                when (it.status) {
                    SUCCESS -> navigator.navigateToMain()
                    else -> this.decorator?.showFingerprint(false)
                }
            }, { this.decorator?.showFingerprint(false) })
        }
    }

    fun dispose() {
        disposable?.dispose()
        this.decorator = null
    }
}
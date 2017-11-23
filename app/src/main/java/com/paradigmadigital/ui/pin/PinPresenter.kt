package com.paradigmadigital.ui.pin

import com.github.ajalt.reprint.core.AuthenticationResult
import com.paradigmadigital.navigation.Navigator
import com.paradigmadigital.repository.preferences.Preferences
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
            val a = preferences.pin
            if (pin == preferences.pin) navigator.navigateToMain()
        }
    }

    fun initialize(decorator: PinUserInterface) {
        this.decorator = decorator
        this.decorator?.initialize(delegate)

        if (preferences.allowFingerPrint) {
            disposable = fingerprintManager.startAuth().subscribe({
                if (it.status == AuthenticationResult.Status.SUCCESS) navigator.navigateToMain()
            }, {})
        }
    }

    fun dispose() {
        disposable?.dispose()
        this.decorator = null
    }
}
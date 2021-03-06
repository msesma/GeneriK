package eu.sesma.generik.ui.pin

import android.util.Log
import com.github.ajalt.reprint.core.AuthenticationFailureReason
import com.github.ajalt.reprint.core.AuthenticationResult
import com.github.ajalt.reprint.core.AuthenticationResult.Status.*
import com.github.ajalt.reprint.rxjava2.RxReprint
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class FingerprintManager
@Inject constructor() {
    private val TAG = FingerprintManager::class.simpleName

    //We are only interested in the first result, so let's convert from Flowable to Single
     fun startAuth(): Single<AuthenticationResult> {
        return RxReprint.authenticate()
                .firstOrError()
                .doOnSuccess {
                    when (it.status) {
                        SUCCESS -> Unit
                        NONFATAL_FAILURE -> showError(it.failureReason, it.errorMessage)
                        FATAL_FAILURE -> showError(it.failureReason, it.errorMessage)
                        null -> Unit
                    }
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    //Sample code, but consider informing the user about the problem found.
    private fun showError(reason: AuthenticationFailureReason?, message: CharSequence?) =
            Log.e(TAG, "$reason, $message")
}
package com.paradigmadigital.ui.login

import android.content.Context
import android.hardware.fingerprint.FingerprintManager
import android.util.Log
import com.multidots.fingerprintauth.FingerPrintAuthCallback
import com.multidots.fingerprintauth.FingerPrintAuthHelper
import com.paradigmadigital.platform.CallbackFun
import com.paradigmadigital.repository.Repository
import javax.inject.Inject


class FingerprintManager
@Inject
constructor(
        val context: Context,
        val repository: Repository
) {
    private val TAG = FingerprintManager::class.simpleName
    val authCallback = object : FingerPrintAuthCallback {
        override fun onNoFingerPrintHardwareFound() {
            Log.d(TAG, "onNoFingerPrintHardwareFound")
            stopAuth()
        }

        override fun onAuthFailed(errorCode: Int, errorMessage: String?) {
            Log.d(TAG, "onAuthFailed: " + errorMessage)
            callback?.invoke(false)
            stopAuth()
        }

        override fun onNoFingerPrintRegistered() {
            Log.d(TAG, "onNoFingerPrintRegistered")
            stopAuth()
//          TODO: Show dialog to the user offering them to enroll fingerprints:
//                FingerPrintUtils.openSecuritySettings(context);
        }

        override fun onBelowMarshmallow() {
            Log.d(TAG, "onBelowMarshmallow")
            stopAuth()
        }

        override fun onAuthSuccess(cryptoObject: FingerprintManager.CryptoObject?) {
            Log.d(TAG, "onAuthSuccess: " + cryptoObject.toString())
            callback?.invoke(true)
            stopAuth()
        }
    }
    val fingerprintAuthHelper = FingerPrintAuthHelper.getHelper(context, authCallback)
    var callback: CallbackFun<Boolean>? = null


    fun startAuth(callback: CallbackFun<Boolean>) {
//        if (!repository.isFingerPrintAuthdataAvailable) {
//            Log.d(TAG, "Email or Password not stored")
//            return
//        }
        this.callback = callback
        fingerprintAuthHelper.startAuth()
    }

    private fun stopAuth() {
        fingerprintAuthHelper.stopAuth()
    }
}
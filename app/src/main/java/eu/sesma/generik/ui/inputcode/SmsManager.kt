package eu.sesma.generik.ui.inputcode

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.auth.api.phone.SmsRetrieverClient
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status
import eu.sesma.generik.platform.CallbackFun
import javax.inject.Inject

//http://android-developers.googleblog.com/2017/10/effective-phone-number-verification.html

class SmsManager
@Inject
constructor(
        private val context: Context,
        private val smsClient: SmsRetrieverClient
) {
    var callback: CallbackFun<String>? = null

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val extras = intent.extras
            val status = extras.get(SmsRetriever.EXTRA_STATUS) as Status

            if (status.statusCode == CommonStatusCodes.SUCCESS) {
                val securityCode = extras.get(SmsRetriever.EXTRA_SMS_MESSAGE) as String
                callback?.invoke(securityCode)
            }
        }
    }

    fun initialize(callback: CallbackFun<String>) {
        this.callback = callback
        smsClient.startSmsRetriever()
        context.registerReceiver(receiver, IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION))
    }

    fun dispose(){
        context.unregisterReceiver(receiver)
    }
}
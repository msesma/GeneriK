package com.paradigmadigital.ui.inputcode

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.auth.api.phone.SmsRetrieverClient
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status
import com.paradigmadigital.platform.Callback
import javax.inject.Inject


class SmsManager
@Inject
constructor(
        val context: Context,
        val smsClient: SmsRetrieverClient
) {
    var callback: Callback<String>? = null

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

    fun initialize(callback: Callback<String>) {
        this.callback = callback
        smsClient.startSmsRetriever()
        context.registerReceiver(receiver, IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION))
    }

    fun dispose(){
        context.unregisterReceiver(receiver)
    }
}
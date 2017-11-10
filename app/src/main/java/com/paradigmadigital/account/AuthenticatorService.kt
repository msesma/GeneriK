package com.paradigmadigital.account

import android.app.Service
import android.content.Intent
import android.os.IBinder


class AuthenticatorService : Service() {

    private lateinit var authenticator: AccountAuthenticator

    override fun onCreate() {
        authenticator = AccountAuthenticator(this)
    }

    override fun onBind(intent: Intent): IBinder {
        return authenticator.getIBinder()
    }
}
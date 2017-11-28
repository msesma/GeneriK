package com.paradigmadigital.notification

import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService


class FbIdService : FirebaseInstanceIdService() {

    companion object {
        private val TAG = FbIdService::class.simpleName
    }

    override fun onTokenRefresh() {

        // Get updated InstanceID token.
        val refreshedToken = FirebaseInstanceId.getInstance().token
        Log.d(TAG, "Refreshed token: " + refreshedToken!!)

        // TODO: Implement this method to send any registration to your app's servers.
        sendRegistrationToServer(refreshedToken)
    }

    // Persist token to third-party servers.
    // Modify this method to associate the user's FCM InstanceID token with any server-side account
    // maintained by your application.
    private fun sendRegistrationToServer(token: String) {
        // Add custom implementation, as needed.
    }
}
package eu.sesma.generik.notification

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FbMessagingService : FirebaseMessagingService() {

    companion object {
        private val TAG = FbMessagingService::class.simpleName
    }

    override fun onMessageReceived(message: RemoteMessage?) {

        // TODO: Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated.

        Log.d(TAG, "From: " + message?.getFrom())
        Log.d(TAG, "Notification Message Body: " + message?.getNotification()?.getBody())
    }

}
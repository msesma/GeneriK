package com.paradigmadigital.platform

import android.app.Activity
import android.app.Application
import android.arch.lifecycle.ProcessLifecycleOwner
import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.support.multidex.MultiDex
import android.util.Log
import com.crashlytics.android.Crashlytics
import com.facebook.stetho.Stetho
import com.github.ajalt.reprint.core.Reprint
import com.google.firebase.iid.FirebaseInstanceId
import com.paradigmadigital.injection.ApplicationComponent
import com.paradigmadigital.injection.DaggerApplicationComponent
import io.fabric.sdk.android.Fabric


class AndroidApplication : Application() {

    companion object {
        private val TAG = AndroidApplication::class.simpleName
        private val SECURE_PREFERENCES = "secure_preferences"
    }

    val applicationComponent = createComponent()
    val activityLifecycleCallback = object : ActivityLifecycleAdapter() {
        override fun onActivityCreated(activity: Activity, bundle: Bundle?) {
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
    }

    override fun onCreate() {
        super.onCreate()
        Fabric.with(this, Crashlytics())
        registerActivityLifecycleCallbacks(activityLifecycleCallback)
        ProcessLifecycleOwner.get().lifecycle.addObserver(AppLifecycleObserver())
        Stetho.initializeWithDefaults(this)
        Reprint.initialize(this)

        Log.d(TAG, "Instance ID: " + FirebaseInstanceId.getInstance().getToken())
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    protected fun createComponent(): ApplicationComponent {
        return DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }
}

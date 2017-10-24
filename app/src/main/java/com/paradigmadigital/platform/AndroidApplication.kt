package com.paradigmadigital.platform

import android.app.Activity
import android.app.Application
import android.arch.lifecycle.ProcessLifecycleOwner
import android.content.pm.ActivityInfo
import android.os.Bundle
import com.crashlytics.android.Crashlytics
import com.facebook.stetho.Stetho
import com.paradigmadigital.injection.ApplicationComponent
import com.paradigmadigital.injection.DaggerApplicationComponent
import com.q42.qlassified.Qlassified
import com.q42.qlassified.Storage.QlassifiedSharedPreferencesService
import io.fabric.sdk.android.Fabric


class AndroidApplication : Application() {

    companion object {
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
        Stetho.initializeWithDefaults(this);
        initializeEncryptedPreferences()
    }

    private fun initializeEncryptedPreferences() {
        Qlassified.Service.start(this)
        Qlassified.Service.setStorageService(QlassifiedSharedPreferencesService(this, SECURE_PREFERENCES))
    }

    protected fun createComponent(): ApplicationComponent {
        return DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }
}

package com.paradigmadigital.platform

import android.app.Activity
import android.app.Application
import android.content.pm.ActivityInfo
import android.os.Bundle
import com.crashlytics.android.Crashlytics
import com.paradigmadigital.injection.ApplicationComponent
import com.paradigmadigital.injection.DaggerApplicationComponent
import io.fabric.sdk.android.Fabric


class AndroidApplication : Application() {

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
    }

    protected fun createComponent(): ApplicationComponent {
        return DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }
}

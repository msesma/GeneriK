package com.paradigmadigital.platform

import android.app.Activity
import android.app.Application
import android.content.pm.ActivityInfo
import android.os.Bundle
import com.paradigmadigital.injection.ApplicationComponent
import com.paradigmadigital.injection.DaggerApplicationComponent


class AndroidApplication : Application() {

    val applicationComponent = createComponent()
    val activityLifecycleCallback = object : ActivityLifecycleAdapter() {
        override fun onActivityCreated(activity: Activity, bundle: Bundle?) {
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
    }

    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(activityLifecycleCallback)
    }

    protected fun createComponent(): ApplicationComponent {
        return DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }
}

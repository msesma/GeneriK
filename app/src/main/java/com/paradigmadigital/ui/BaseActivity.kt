package com.paradigmadigital.ui

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import com.paradigmadigital.injection.ActivityComponent
import com.paradigmadigital.injection.ApplicationComponent
import com.paradigmadigital.injection.DaggerActivityComponent
import com.paradigmadigital.platform.ActivityModule
import com.paradigmadigital.platform.AndroidApplication


abstract class BaseActivity : AppCompatActivity() {

    fun Activity.getRootView(): ViewGroup = (this.findViewById<ViewGroup>(android.R.id.content) as ViewGroup)
            .getChildAt(0) as ViewGroup

    private val applicationComponent: ApplicationComponent
        get() = (application as AndroidApplication).applicationComponent

    lateinit var activityComponent: ActivityComponent

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent = DaggerActivityComponent.builder()
                .applicationComponent(applicationComponent)
                .activityModule(ActivityModule(this))
                .build()
    }
}

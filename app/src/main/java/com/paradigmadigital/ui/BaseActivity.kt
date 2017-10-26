package com.paradigmadigital.ui

import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import com.paradigmadigital.injection.ActivityComponent
import com.paradigmadigital.injection.ApplicationComponent
import com.paradigmadigital.injection.DaggerActivityComponent
import com.paradigmadigital.platform.ActivityModule
import com.paradigmadigital.platform.AndroidApplication
import com.paradigmadigital.ui.viewmodels.ResultViewModel


abstract class BaseActivity : AppCompatActivity() {

    lateinit var resultViewModel: ResultViewModel

    private val applicationComponent: ApplicationComponent
        get() = (application as AndroidApplication).applicationComponent

    protected lateinit var activityComponent: ActivityComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent = DaggerActivityComponent.builder()
                .applicationComponent(applicationComponent)
                .activityModule(ActivityModule(this))
                .build()

        resultViewModel = ViewModelProviders.of(this).get(ResultViewModel::class.java)
    }

    fun Activity.getRootView() = (window.decorView as ViewGroup).getChildAt(0) as ViewGroup
}

package com.paradigmadigital.ui.main

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.paradigmadigital.R
import com.paradigmadigital.ui.LoggedInBaseActivity
import javax.inject.Inject

class MainActivity : LoggedInBaseActivity() {

    @Inject
    lateinit var decorator: MainDecorator
    @Inject
    lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.fadein, R.anim.fadeout)
        setContentView(R.layout.activity_main)
        activityComponent.inject(this)


        decorator.bind(getRootView())
        presenter.initialize(decorator)
    }

    override fun onDestroy() {
        super.onDestroy()
        decorator.dispose()
        presenter.dispose()
    }
}
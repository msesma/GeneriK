package com.paradigmadigital.ui.main

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.paradigmadigital.R
import com.paradigmadigital.ui.BaseActivity
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject
    lateinit var decorator: MainDecorator
    @Inject
    lateinit var presenter: MainPresenter

    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.fadein, R.anim.fadeout)
        setContentView(R.layout.activity_main)
        activityComponent.inject(this)

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        decorator.bind(getRootView())
        presenter.initialize(decorator, viewModel)
    }

    override fun onDestroy() {
        super.onDestroy()
        decorator.dispose()
        presenter.dispose()
    }
}
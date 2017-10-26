package com.paradigmadigital.ui.login

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.paradigmadigital.R
import com.paradigmadigital.ui.BaseActivity
import com.paradigmadigital.ui.viewmodels.UserViewModel
import javax.inject.Inject

class LoginActivity : BaseActivity() {

    @Inject
    lateinit var decorator: LoginDecorator
    @Inject
    lateinit var presenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        activityComponent.inject(this)

        val userViewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)

        decorator.bind(getRootView())
        presenter.initialize(decorator, userViewModel, resultViewModel)
    }

    override fun onDestroy() {
        super.onDestroy()
        decorator.dispose()
        presenter.dispose()
    }
}
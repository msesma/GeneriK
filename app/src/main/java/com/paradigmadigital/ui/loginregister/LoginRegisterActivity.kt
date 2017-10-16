package com.paradigmadigital.ui.loginregister

import android.os.Bundle
import com.paradigmadigital.R
import com.paradigmadigital.ui.BaseActivity
import javax.inject.Inject

class LoginRegisterActivity : BaseActivity() {

    @Inject
    lateinit var decorator: LoginRegisterDecorator
    @Inject
    lateinit var presenter: LoginRegisterPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        setContentView(R.layout.activity_login_register)
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

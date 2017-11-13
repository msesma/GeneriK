package com.paradigmadigital.ui.changepass

import android.os.Bundle
import com.paradigmadigital.R
import com.paradigmadigital.navigation.Navigator
import com.paradigmadigital.ui.BaseActivity
import javax.inject.Inject

class ChangePassActivity : BaseActivity() {

    @Inject
    lateinit var decorator: ChangePassDecorator
    @Inject
    lateinit var presenter: ChangePassPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_pass)
        activityComponent.inject(this)

        decorator.bind(getRootView())
        presenter.initialize(decorator, intent.extras.getString(Navigator.EXTRA_EMAIL))
    }


    override fun onDestroy() {
        super.onDestroy()
        decorator.dispose()
        presenter.dispose()
    }
}
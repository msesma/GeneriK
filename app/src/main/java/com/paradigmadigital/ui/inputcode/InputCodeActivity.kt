package com.paradigmadigital.ui.inputcode

import android.os.Bundle
import com.paradigmadigital.postoscarrefour.R
import com.paradigmadigital.ui.BaseActivity
import javax.inject.Inject

class InputCodeActivity : BaseActivity() {

    @Inject
    lateinit var decorator: InputCodeDecorator
    @Inject
    lateinit var presenter: InputCodePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_code)
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
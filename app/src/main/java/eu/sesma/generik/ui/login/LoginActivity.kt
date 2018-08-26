package eu.sesma.generik.ui.login

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import eu.sesma.generik.R
import eu.sesma.generik.ui.BaseActivity
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

        decorator.bind(getRootView())
        presenter.initialize(decorator, resultViewModel)
    }

    override fun onDestroy() {
        super.onDestroy()
        decorator.dispose()
        presenter.dispose()
    }
}
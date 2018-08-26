package eu.sesma.generik.ui.pin

import android.os.Bundle
import eu.sesma.generik.R
import eu.sesma.generik.ui.BaseActivity
import javax.inject.Inject

class PinActivity : BaseActivity() {

    @Inject
    lateinit var decorator: PinDecorator
    @Inject
    lateinit var presenter: PinPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pin)
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
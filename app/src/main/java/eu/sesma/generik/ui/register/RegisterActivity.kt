package eu.sesma.generik.ui.register

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.google.android.gms.auth.api.credentials.Credential
import eu.sesma.generik.R
import eu.sesma.generik.ui.BaseActivity
import eu.sesma.generik.ui.register.RegisterDecorator.Companion.RESOLVE_HINT
import javax.inject.Inject


class RegisterActivity : BaseActivity() {

    @Inject
    lateinit var decorator: RegisterDecorator
    @Inject
    lateinit var presenter: RegisterPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        activityComponent.inject(this)

        decorator.bind(getRootView())
        presenter.initialize(decorator, resultViewModel)
    }

    override fun onDestroy() {
        super.onDestroy()
        decorator.dispose()
        presenter.dispose()
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RESOLVE_HINT && resultCode == Activity.RESULT_OK) {
            val credential: Credential = data.getParcelableExtra(Credential.EXTRA_KEY)
            presenter.setPhone(credential.id)
        }
    }
}
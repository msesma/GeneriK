package com.paradigmadigital.ui.loginregister

import android.view.View
import butterknife.ButterKnife
import butterknife.OnClick
import com.paradigmadigital.R
import javax.inject.Inject


class LoginRegisterDecorator
@Inject constructor(): LoginRegisterUserInterface {

    private var delegate: LoginRegisterUserInterface.Delegate? = null

    fun bind(view: View) {
        ButterKnife.bind(this, view)
    }

    fun dispose() {
        delegate = null
    }

    override fun initialize(delegate: LoginRegisterUserInterface.Delegate) {
        this.delegate = delegate
    }

    @OnClick(R.id.bt_login)
    fun onLoginClick() {
        delegate?.onLogin()
    }

    @OnClick(R.id.bt_register)
    fun onRegisterClick() {
        delegate?.onRegister()
    }

    @OnClick(R.id.bt_terms)
    fun onTermsClick() {
        delegate?.onTerms()
    }
}
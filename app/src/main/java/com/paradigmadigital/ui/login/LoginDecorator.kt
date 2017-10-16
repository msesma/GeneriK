package com.paradigmadigital.ui.login

import android.text.TextUtils
import android.view.View
import android.widget.EditText
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.paradigmadigital.R
import com.paradigmadigital.ui.AlertDialog
import com.paradigmadigital.ui.BaseActivity
import javax.inject.Inject


class LoginDecorator
@Inject constructor(
        val activity: BaseActivity,
        val dialog: AlertDialog
) : LoginUserInterface {

    @BindView(R.id.et_email)
    lateinit var email: EditText
    @BindView(R.id.et_pass)
    lateinit var pass: EditText

    private var delegate: LoginUserInterface.Delegate? = null

    fun bind(view: View) {
        ButterKnife.bind(this, view)
        initToolbar()
    }

    fun dispose() {
        delegate = null
    }

    override fun initialize(delegate: LoginUserInterface.Delegate) {
        this.delegate = delegate
    }

    private fun initToolbar() {
        val actionBar = activity.supportActionBar
        actionBar?.setDisplayShowTitleEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }

    @OnClick(R.id.bt_login)
    fun onLoginClick() {
        val emailText = email.text.toString()
        if (TextUtils.isEmpty(emailText) || !android.util.Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
            email.error = activity.getString(R.string.email_format)
            return
        }

        val passText = pass.text.toString()
        if (TextUtils.isEmpty(passText)) {
            pass.error = activity.getString(R.string.pass_empty)
            return
        }

        delegate?.onLogin(email = emailText, pass = passText)
    }

    @OnClick(R.id.bt_forgot)
    fun onForgotClick() = dialog.show(R.string.confirm_pass_change, R.string.empty, { delegate?.onForgotPassword() })
}
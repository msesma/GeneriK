package com.paradigmadigital.ui.login

import android.arch.lifecycle.Observer
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import butterknife.OnFocusChange
import com.paradigmadigital.R
import com.paradigmadigital.platform.Constants.LOGIN
import com.paradigmadigital.repository.NetworkResult
import com.paradigmadigital.repository.NetworkResultCode.SUCCESS
import com.paradigmadigital.ui.AlertDialog
import com.paradigmadigital.ui.BaseActivity
import com.paradigmadigital.ui.BaseDecorator
import com.paradigmadigital.ui.ResultViewModel
import javax.inject.Inject


class LoginDecorator
@Inject constructor(
        private val activity: BaseActivity,
        private val dialog: AlertDialog
) : BaseDecorator(dialog), LoginUserInterface {

    companion object {
        val REQUEST_LOGIN = LOGIN + 0
    }

    @BindView(R.id.et_email)
    lateinit var email: EditText
    @BindView(R.id.et_pass)
    lateinit var pass: EditText
    @BindView(R.id.bt_forgot)
    lateinit var forgot: Button

    private var delegate: LoginUserInterface.Delegate? = null

    fun bind(view: View) {
        ButterKnife.bind(this, view)
        initToolbar()
    }

    fun dispose() {
        delegate = null
    }

    override fun initialize(delegate: LoginUserInterface.Delegate, resultViewModel: ResultViewModel) {
        this.delegate = delegate
        resultViewModel.result.observe(activity, Observer<NetworkResult> { handleResult(it) })
    }

    private fun initToolbar() {
        val actionBar = activity.supportActionBar
        actionBar?.setDisplayShowTitleEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }

    @OnFocusChange(R.id.et_email)
    fun onEmailEntered(focused: Boolean) {
        if (focused) return
        forgot.isEnabled = android.util.Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches()
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

        startWaitingMode()
        delegate?.onLogin(email = emailText, pass = passText)
    }

    @OnClick(R.id.bt_forgot)
    fun onForgotClick() = dialog.show(R.string.confirm_pass_change, R.string.empty, false) { delegate?.onForgotPassword(email.text.toString()) }

    override fun handleResult(result: NetworkResult?) {
        if (result?.requestId !in LOGIN..LOGIN + 99) return

        stopWaitingMode()
        if (result?.result != SUCCESS) {
            super.handleResult(result)
            return
        }
        if (delegate?.onLoggedIn() == false) dialog.show(R.string.login_error, R.string.empty, true) { }
    }
}
package com.paradigmadigital.ui.login

import android.arch.lifecycle.Observer
import android.support.design.widget.TextInputEditText
import android.text.TextUtils
import android.view.View
import android.widget.Button
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import butterknife.OnFocusChange
import com.paradigmadigital.R
import com.paradigmadigital.domain.entities.User
import com.paradigmadigital.platform.Constants.LOGIN
import com.paradigmadigital.repository.NetworkResult
import com.paradigmadigital.repository.NetworkResultCode.FORBIDDEN
import com.paradigmadigital.repository.NetworkResultCode.SUCCESS
import com.paradigmadigital.ui.AlertDialog
import com.paradigmadigital.ui.BaseActivity
import com.paradigmadigital.ui.BaseDecorator
import com.paradigmadigital.ui.viewmodels.ResultViewModel
import com.paradigmadigital.ui.viewmodels.UserViewModel
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
    lateinit var email: TextInputEditText
    @BindView(R.id.et_pass)
    lateinit var pass: TextInputEditText
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

    override fun initialize(
            delegate: LoginUserInterface.Delegate,
            userViewModel: UserViewModel,
            resultViewModel: ResultViewModel) {
        this.delegate = delegate

        userViewModel.userLiveData.observe(activity, Observer<User> { handleUserChanges(it) })
        resultViewModel.result.observe(activity, Observer<NetworkResult> { handleResult(it) })
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

    private fun initToolbar() {
        val actionBar = activity.supportActionBar
        actionBar?.setDisplayShowTitleEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun handleUserChanges(user: User?) {
        if (!user?.token.isNullOrEmpty()) delegate?.onLoggedIn()
    }

    override fun handleResult(result: NetworkResult?) {
        if (result?.requestId !in LOGIN..LOGIN + 99) return

        stopWaitingMode()
        when (result?.result) {
            FORBIDDEN -> dialog.show(R.string.login_error, R.string.empty, true) { }
            SUCCESS -> {
            }
            else -> super.handleResult(result)
        }
    }
}
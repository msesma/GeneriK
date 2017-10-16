package com.paradigmadigital.ui.register

import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.EditText
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.paradigmadigital.R
import com.paradigmadigital.ui.BaseActivity
import javax.inject.Inject

class RegisterDecorator
@Inject constructor(
        val activity: BaseActivity
) : RegisterUserInterface {

    @BindView(R.id.et_name)
    lateinit var name: EditText
    @BindView(R.id.et_tel)
    lateinit var tel: EditText
    @BindView(R.id.et_email1)
    lateinit var email1: EditText
    @BindView(R.id.et_email2)
    lateinit var email2: EditText
    @BindView(R.id.et_pass1)
    lateinit var pass1: EditText
    @BindView(R.id.et_pass2)
    lateinit var pass2: EditText

    private var delegate: RegisterUserInterface.Delegate? = null

    fun bind(view: View) {
        ButterKnife.bind(this, view)
        initToolbar()
    }

    fun dispose() {
        delegate = null
    }

    override fun initialize(delegate: RegisterUserInterface.Delegate) {
        this.delegate = delegate
    }

    private fun initToolbar() {
        val actionBar = activity.supportActionBar
        actionBar?.setDisplayShowTitleEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }

    @OnClick(R.id.bt_register)
    fun onRegisterClick() {
        var ok = true
        ok = verifyEmail(ok)
        ok = verifyPassword(ok)

        if (ok) delegate?.onRegister(
                name = name.text.toString(),
                email = email1.text.toString(),
                tel = tel.text.toString(),
                pass = pass1.text.toString()
        )
    }

    private fun verifyPassword(ok: Boolean): Boolean {
        var ok1 = ok
        val pass1Text = pass1.text.toString()
        if (TextUtils.isEmpty(pass1Text)) {
            pass1.error = activity.getString(R.string.pass_empty)
            ok1 = false
        }

        val pass2Text = pass2.text.toString()
        if (TextUtils.isEmpty(pass2Text)) {
            pass2.error = activity.getString(R.string.pass_empty)
            ok1 = false
        }

        if (pass1Text != pass2Text) {
            pass2.error = activity.getString(R.string.pass_not_match)
            ok1 = false
        }
        return ok1
    }

    private fun verifyEmail(ok: Boolean): Boolean {
        var ok1 = ok
        val email1Text = email1.text.toString()
        if (TextUtils.isEmpty(email1Text) || !Patterns.EMAIL_ADDRESS.matcher(email1Text).matches()) {
            email1.error = activity.getString(R.string.email_format)
            ok1 = false
        }

        val email2Text = email2.text.toString()
        if (TextUtils.isEmpty(email2Text) || !Patterns.EMAIL_ADDRESS.matcher(email2Text).matches()) {
            email2.error = activity.getString(R.string.email_format)
            ok1 = false
        }

        if (email1Text != email2Text) {
            email2.error = activity.getString(R.string.email_match)
            ok1 = false
        }
        return ok1
    }
}
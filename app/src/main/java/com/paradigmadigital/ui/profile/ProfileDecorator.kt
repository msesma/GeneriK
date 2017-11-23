package com.paradigmadigital.ui.profile

import android.os.Build
import android.support.annotation.StringRes
import android.support.design.widget.TextInputEditText
import android.support.v4.app.ActivityCompat.startIntentSenderForResult
import android.support.v7.widget.Toolbar
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import butterknife.OnFocusChange
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.credentials.HintRequest
import com.google.android.gms.common.api.GoogleApiClient
import com.paradigmadigital.R
import com.paradigmadigital.domain.entities.User
import com.paradigmadigital.navigation.DrawerManager
import com.paradigmadigital.repository.ApiResult
import com.paradigmadigital.ui.AlertDialog
import com.paradigmadigital.ui.BaseActivity
import com.paradigmadigital.ui.BaseDecorator
import javax.inject.Inject


class ProfileDecorator
@Inject constructor(
        private val activity: BaseActivity,
        private val apiClient: GoogleApiClient,
        dialog: AlertDialog,
        private val drawerManager: DrawerManager
) : BaseDecorator(dialog), ProfileUserInterface {

    companion object {
        val RESOLVE_HINT = 100
    }

    @BindView(R.id.et_name)
    lateinit var name: TextInputEditText
    @BindView(R.id.et_tel)
    lateinit var tel: TextInputEditText
    @BindView(R.id.et_email1)
    lateinit var email1: TextInputEditText
    @BindView(R.id.et_email2)
    lateinit var email2: TextInputEditText
    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar

    private var delegate: ProfileUserInterface.Delegate? = null

    fun bind(view: View) {
        ButterKnife.bind(this, view)
        initToolbar()
        val drawer = drawerManager.configureDrawer(toolbar)
        drawer.setSelection(R.id.profile.toLong(), false)
    }

    fun dispose() {
        delegate = null
    }

    override fun initialize(
            delegate: ProfileUserInterface.Delegate,
            user: User
    ) {
        this.delegate = delegate
        name.setText(user.name)
        tel.setText(user.phone)
        email1.setText(user.email)
        email2.setText(user.email)
    }

    override fun setPhone(phone: String) {
        tel.setText(phone)
    }

    override fun onResult(result: ApiResult) {
        when (result) {
            is ApiResult.Success<*> -> {
                delegate?.onProfileEdited()
                stopWaitingMode()
            }
            is ApiResult.Failure -> showError()
        }
    }

    private fun showError() {
        stopWaitingMode()
        showToast(R.string.server_error)
    }

    private fun showToast(@StringRes text: Int) = Toast.makeText(activity, text, Toast.LENGTH_SHORT).show()

    @OnClick(R.id.bt_update)
    fun onRegisterClick() {
        var ok = verifyPhone()
        ok = verifyEmail(ok)

        if (ok) {
            delegate?.onProfileEdit(
                    name = name.text.toString(),
                    email = email1.text.toString(),
                    tel = tel.text.toString()
            )
            startWaitingMode()
        }
    }

    @OnFocusChange(R.id.et_tel)
    fun onTelFieldFocused(focused: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O || !focused) return
        requestHint()
    }

    private fun requestHint() {
        val hintRequest = HintRequest.Builder()
                .setPhoneNumberIdentifierSupported(true)
                .build()

        val intent = Auth.CredentialsApi.getHintPickerIntent(apiClient, hintRequest)

        startIntentSenderForResult(activity, intent.intentSender,
                RESOLVE_HINT, null, 0, 0, 0, null)
    }

    private fun verifyPhone(): Boolean {
        val ok = android.util.Patterns.PHONE.matcher(tel.text.toString()).matches()
        if (!ok) tel.error = activity.getString(R.string.phone_empty)
        return ok
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

    private fun initToolbar() {
        activity.setSupportActionBar(toolbar)
        val actionBar = activity.supportActionBar
        actionBar?.setDisplayShowTitleEnabled(true)
        actionBar?.setIcon(R.mipmap.ic_launcher)
    }


}
package com.paradigmadigital.ui.inputcode

import android.arch.lifecycle.Observer
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.paradigmadigital.R
import com.paradigmadigital.repository.NetworkResult
import com.paradigmadigital.repository.NetworkResultCode.FAIL
import com.paradigmadigital.repository.NetworkResultCode.SUCCESS
import com.paradigmadigital.ui.AlertDialog
import com.paradigmadigital.ui.BaseActivity
import com.paradigmadigital.ui.BaseDecorator
import com.paradigmadigital.ui.ResultViewModel
import javax.inject.Inject


class InputCodeDecorator
@Inject constructor(
        private val activity: BaseActivity,
        private val dialog: AlertDialog
) : BaseDecorator(dialog), InputCodeUserInterface {

    companion object {
        val REQUEST_CODE = 0
        val REQUEST_SET_PASS = 1
        private val CODE_LEN = 6
    }

    @BindView(R.id.tv_code)
    lateinit var code: TextView

    private var codeText = ""

    private var delegate: InputCodeUserInterface.Delegate? = null

    fun bind(view: View) {
        ButterKnife.bind(this, view)
        initToolbar()
    }

    fun dispose() {
        delegate = null
    }

    override fun initialize(delegate: InputCodeUserInterface.Delegate, resultViewModel: ResultViewModel) {
        this.delegate = delegate
        resultViewModel.result.observe(activity, Observer<NetworkResult> { handleResult(it) })
    }

    override fun autoComplete(text: String) {
        if (text.length != CODE_LEN) return
        text.forEach { codeText += "$it " }
        code.text = codeText
        sendCode()
    }

    private fun initToolbar() {
        val actionBar = activity.supportActionBar
        actionBar?.setDisplayShowTitleEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setHomeAsUpIndicator(R.drawable.ic_close_white_24dp)
    }

    @OnClick(R.id.bt_0, R.id.bt_1, R.id.bt_2, R.id.bt_3, R.id.bt_4, R.id.bt_5, R.id.bt_6, R.id.bt_7, R.id.bt_8, R.id.bt_9)
    fun onNumberClick(button: Button) {
        if (codeText.length >= CODE_LEN * 2) return
        when (button.id) {
            R.id.bt_0 -> codeText += "0 "
            R.id.bt_1 -> codeText += "1 "
            R.id.bt_2 -> codeText += "2 "
            R.id.bt_3 -> codeText += "3 "
            R.id.bt_4 -> codeText += "4 "
            R.id.bt_5 -> codeText += "5 "
            R.id.bt_6 -> codeText += "6 "
            R.id.bt_7 -> codeText += "7 "
            R.id.bt_8 -> codeText += "8 "
            R.id.bt_9 -> codeText += "9 "
        }
        code.text = codeText
        if (codeText.length == CODE_LEN * 2) sendCode()
    }

    private fun sendCode() {
        val rawCode = codeText.filter { it != ' ' }
        startWaitingMode()
        Handler().postDelayed(Runnable {
            delegate?.onCode(rawCode)
        }, 250)
    }

    @OnClick(R.id.bt_back)
    fun onBackClick() {
        codeText = codeText.dropLast(2)
        code.text = codeText
    }

    @OnClick(R.id.bt_new)
    fun onNewClick() {
        codeText = ""
        code.text = ""
        delegate?.onSendNew()
    }

    override fun handleResult(result: NetworkResult?) {
        stopWaitingMode()
        if (result == null) return
        when {
            result.result == FAIL && result.requestId == REQUEST_SET_PASS ->
                dialog.show(R.string.incorrect_code, R.string.empty, true) { }
            result.result == SUCCESS && result.requestId == REQUEST_CODE -> return
            result.result == SUCCESS && result.requestId == REQUEST_SET_PASS -> delegate?.onCodeSent(true)
            result.requestId == REQUEST_CODE ->
                dialog.show(R.string.unknown_error, R.string.connection_error, true) { }
            result.requestId == REQUEST_SET_PASS ->
                dialog.show(R.string.unknown_error, R.string.pass_not_updated, true)
                { delegate?.onCodeSent(false) }
        }
    }
}
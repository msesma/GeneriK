package com.paradigmadigital.ui

import android.support.annotation.Nullable
import android.support.constraint.ConstraintLayout
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import butterknife.BindView
import com.paradigmadigital.R
import com.paradigmadigital.repository.NetworkResult


abstract class BaseDecorator
constructor(
        private val dialog: AlertDialog
) {

    @Nullable
    @JvmField
    @BindView(R.id.wait_progress)
    var wait: ConstraintLayout? = null

    fun startWaitingMode() {
        wait?.visibility = VISIBLE
    }

    fun stopWaitingMode() {
        wait?.visibility = INVISIBLE
    }

    open fun handleResult(result: NetworkResult?) {
        stopWaitingMode()
        if (result == null) return
        when (result) {
            NetworkResult.SUCCESS -> return
            NetworkResult.DISCONNECTED -> {
                dialog.show(R.string.connection_error, R.string.empty, { })
            }
            NetworkResult.BAD_URL -> {
                dialog.show(R.string.server_error, R.string.empty, { })
            }
            NetworkResult.UNKNOWN -> {
                dialog.show(R.string.unknown_error, R.string.empty, { })
            }
        }
    }
}
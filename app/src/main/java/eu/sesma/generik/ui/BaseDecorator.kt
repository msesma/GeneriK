package eu.sesma.generik.ui

import android.support.annotation.Nullable
import android.support.constraint.ConstraintLayout
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import butterknife.BindView
import eu.sesma.generik.R
import eu.sesma.generik.repository.NetworkResultCode
import eu.sesma.generik.repository.NetworkResultCode.*


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

    open fun handleResult(result: NetworkResultCode?) {
        stopWaitingMode()
        if (result == null) return
        when (result) {
            SUCCESS -> return
            DISCONNECTED -> dialog.show(R.string.connection_error, R.string.empty, true, { })
            BAD_URL -> dialog.show(R.string.server_error, R.string.empty, true, { })
            else -> dialog.show(R.string.unknown_error, R.string.empty, true, { })
        }
    }
}
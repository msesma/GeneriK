package eu.sesma.generik.ui

import android.content.Context
import android.support.annotation.StringRes
import android.support.v7.app.AlertDialog
import eu.sesma.generik.R
import eu.sesma.generik.platform.CallbackFun
import javax.inject.Inject


class AlertDialog
@Inject
constructor(
        private val context: Context
) {
    fun show(@StringRes title: Int, @StringRes text: Int, oneButton: Boolean, callback: CallbackFun<Unit>) {
        val builder = AlertDialog.Builder(context, R.style.AlertDialogStyle)
        builder.setTitle(title)
        builder.setMessage(text)

        configureButtons(builder,oneButton, callback)

        builder.create().show()
    }

    private fun configureButtons(builder: AlertDialog.Builder, oneButton: Boolean, callback: CallbackFun<Unit>) {
        if (oneButton) {
            builder.setPositiveButton(R.string.ok) { _, _ -> callback(Unit) }
            return
        }
        builder.setPositiveButton(R.string.confirm) { _, _ -> callback(Unit) }
        builder.setNegativeButton(R.string.cancel) { dialog, _ -> dialog.cancel() }
    }
}

package com.paradigmadigital.ui

import android.app.Activity
import android.support.annotation.StringRes
import android.support.v7.app.AlertDialog
import com.paradigmadigital.platform.Callback
import com.paradigmadigital.R
import javax.inject.Inject


class AlertDialog
@Inject
constructor(
        private val activity: Activity
) {
    fun show(@StringRes title: Int, @StringRes text: Int, callback: Callback<Unit>) {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle(title)
        builder.setMessage(text)

        builder.setPositiveButton(R.string.confirm) { _, _ -> callback(Unit) }
        builder.setNegativeButton(R.string.cancel) { dialog, _ -> dialog.cancel() }

        builder.create().show()
    }
}

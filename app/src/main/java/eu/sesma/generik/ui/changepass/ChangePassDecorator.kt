package eu.sesma.generik.ui.changepass

import android.support.design.widget.TextInputEditText
import android.text.TextUtils
import android.view.View
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import eu.sesma.generik.R
import eu.sesma.generik.ui.BaseActivity
import javax.inject.Inject

class ChangePassDecorator
@Inject constructor(
        private val activity: BaseActivity
) : ChangePassUserInterface {

    @BindView(R.id.et_pass1)
    lateinit var pass1: TextInputEditText
    @BindView(R.id.et_pass2)
    lateinit var pass2: TextInputEditText

    private var delegate: ChangePassUserInterface.Delegate? = null

    fun bind(view: View) {
        ButterKnife.bind(this, view)
        initToolbar()
    }

    fun dispose() {
        delegate = null
    }

    override fun initialize(delegate: ChangePassUserInterface.Delegate) {
        this.delegate = delegate
    }

    private fun initToolbar() {
        val actionBar = activity.supportActionBar
        actionBar?.setDisplayShowTitleEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }

    @OnClick(R.id.bt_enter)
    fun onEnterClick() {
        val pass1Text = pass1.text.toString()
        if (TextUtils.isEmpty(pass1Text)) {
            pass1.error = activity.getString(R.string.pass_empty)
            return
        }

        val pass2Text = pass2.text.toString()
        if (TextUtils.isEmpty(pass2Text)) {
            pass2.error = activity.getString(R.string.pass_empty)
            return
        }

        if (pass1Text != pass2Text) {
            pass2.error = activity.getString(R.string.pass_not_match)
            return
        }

        delegate?.onNewPass(pass1Text)
    }

}
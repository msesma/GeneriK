package com.paradigmadigital.ui.main

import android.arch.lifecycle.Observer
import android.support.v7.widget.Toolbar
import android.view.View
import butterknife.BindView
import butterknife.ButterKnife
import com.paradigmadigital.R
import com.paradigmadigital.domain.entities.User
import com.paradigmadigital.navigation.DrawerManager
import com.paradigmadigital.ui.BaseActivity
import javax.inject.Inject

class MainDecorator
@Inject constructor(
        private val activity: BaseActivity,
        private val drawerManager: DrawerManager
) : MainUserInterface {

    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar

    private var delegate: MainUserInterface.Delegate? = null

    fun bind(view: View) {
        ButterKnife.bind(this, view)
        initToolbar()
        val drawer = drawerManager.configureDrawer(toolbar)
        drawer.setSelection(R.id.main.toLong())
    }

    fun dispose() {
        delegate = null
    }

    override fun initialize(delegate: MainUserInterface.Delegate, viewModel: MainViewModel) {
        this.delegate = delegate

        viewModel.userLiveData.observe(activity, Observer<User> { user ->
            toolbar.title = activity.getString(R.string.hi, user?.name ?: activity.getString(R.string.user))
        })
    }

    private fun initToolbar() {
        activity.setSupportActionBar(toolbar)
        activity.supportActionBar?.setDisplayShowTitleEnabled(false)
    }
}
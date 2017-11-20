package com.paradigmadigital.ui.main

import android.support.annotation.StringRes
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import com.paradigmadigital.R
import com.paradigmadigital.domain.entities.PostUiModel
import com.paradigmadigital.navigation.DrawerManager
import com.paradigmadigital.ui.BaseActivity
import javax.inject.Inject

class MainDecorator
@Inject
constructor(
        private val activity: BaseActivity,
        private val drawerManager: DrawerManager,
        private val layoutManager: LinearLayoutManager,
        private val adapter: PostAdapter
) : MainUserInterface {

    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar
    @BindView(R.id.main_list)
    lateinit var list: RecyclerView
    @BindView(R.id.swipeRefreshLayout)
    lateinit var swipeRefresh: SwipeRefreshLayout

    private var delegate: MainUserInterface.Delegate? = null

    private val postClickListener = object : PostClickListener {
        override fun onClick(index: Int) {
            delegate?.onClick(adapter.getItemAtPosition(index))
        }
    }

    private var refreshListener: SwipeRefreshLayout.OnRefreshListener =
            SwipeRefreshLayout.OnRefreshListener { delegate?.onRefresh() }

    fun bind(view: View) {
        ButterKnife.bind(this, view)
        initToolbar()
        val drawer = drawerManager.configureDrawer(toolbar)
        drawer.setSelection(R.id.main.toLong())
        list.layoutManager = layoutManager
        list.itemAnimator = DefaultItemAnimator()
        swipeRefresh.setOnRefreshListener(refreshListener)
        setWaitingMode(true)
    }

    fun dispose() {
        delegate = null
    }

    override fun initialize(delegate: MainUserInterface.Delegate) {
        this.delegate = delegate
        toolbar.title = ""
        list.adapter = adapter
        adapter.setClickListener(postClickListener)
    }

    override fun showError(error: Exception) {
        setWaitingMode(false)
        showToast(R.string.server_error)
    }

    override fun showPosts(posts: List<PostUiModel>) {
        setWaitingMode(false)
        list.visibility = if (posts.isEmpty()) INVISIBLE else VISIBLE
        adapter.swap(posts)
    }

    private fun showToast(@StringRes text: Int) = Toast.makeText(activity, text, Toast.LENGTH_SHORT).show()

    private fun setWaitingMode(waitingMode: Boolean) {
        if (waitingMode) {
            list.visibility = INVISIBLE
        }
        swipeRefresh.isRefreshing = waitingMode
    }

    private fun initToolbar() {
        activity.setSupportActionBar(toolbar)
        val actionBar = activity.supportActionBar
        actionBar?.setDisplayShowTitleEnabled(true)
        actionBar?.setIcon(R.mipmap.ic_launcher)
    }
}

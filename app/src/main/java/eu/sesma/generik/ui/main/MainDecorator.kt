package eu.sesma.generik.ui.main

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import butterknife.BindView
import butterknife.ButterKnife
import eu.sesma.generik.R
import eu.sesma.generik.domain.entities.PostUiModel
import eu.sesma.generik.navigation.DrawerManager
import eu.sesma.generik.repository.NetworkResultCode
import eu.sesma.generik.ui.AlertDialog
import eu.sesma.generik.ui.BaseActivity
import eu.sesma.generik.ui.BaseDecorator
import javax.inject.Inject

class MainDecorator
@Inject
constructor(
        private val activity: BaseActivity,
        private val drawerManager: DrawerManager,
        private val layoutManager: LinearLayoutManager,
        private val adapter: PostAdapter,
        alertDialog: AlertDialog
) : MainUserInterface, BaseDecorator(alertDialog) {

    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar
    @BindView(R.id.main_list)
    lateinit var list: RecyclerView
    @BindView(R.id.swipeRefreshLayout)
    lateinit var swipeRefresh: SwipeRefreshLayout

    private var delegate: MainUserInterface.Delegate? = null

    private val postClickListener = object : PostClickListener {
        override fun onClick(index: Int) {
            if (index < 0) return
            delegate?.onClick(adapter.getItemAtPosition(index))
        }
    }

    private var refreshListener: SwipeRefreshLayout.OnRefreshListener =
            SwipeRefreshLayout.OnRefreshListener { delegate?.onRefresh() }

    fun bind(view: View) {
        ButterKnife.bind(this, view)
        initToolbar()
        val drawer = drawerManager.configureDrawer(toolbar)
        drawer.setSelection(R.id.main.toLong(), false)
        list.layoutManager = layoutManager
        list.itemAnimator = DefaultItemAnimator()
        list.adapter = adapter
        adapter.setClickListener(postClickListener)
        swipeRefresh.setOnRefreshListener(refreshListener)
    }

    fun dispose() {
        delegate = null
    }

    override fun initialize(delegate: MainUserInterface.Delegate) {
        this.delegate = delegate
        toolbar.title = ""
        setWaitingMode(true)
    }

    override fun showError(error: NetworkResultCode) {
        setWaitingMode(false)
        handleResult(error)
    }

    override fun showPosts(posts: List<PostUiModel>) {
        setWaitingMode(false)
        list.visibility = if (posts.isEmpty()) INVISIBLE else VISIBLE
        adapter.swap(posts)
    }

    private fun setWaitingMode(waitingMode: Boolean) {
        swipeRefresh.isRefreshing = waitingMode
    }

    private fun initToolbar() {
        activity.setSupportActionBar(toolbar)
        val actionBar = activity.supportActionBar
        actionBar?.setDisplayShowTitleEnabled(true)
        actionBar?.setIcon(R.mipmap.ic_launcher)
    }
}

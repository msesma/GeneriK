package eu.sesma.generik.ui.detail

import android.graphics.Bitmap
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import eu.sesma.generik.R
import eu.sesma.generik.api.images.ImageRepository
import eu.sesma.generik.api.images.ImageTarget
import eu.sesma.generik.api.model.Comment
import eu.sesma.generik.domain.entities.PostUiModel
import eu.sesma.generik.repository.NetworkResultCode
import eu.sesma.generik.ui.AlertDialog
import eu.sesma.generik.ui.BaseActivity
import eu.sesma.generik.ui.BaseDecorator
import com.squareup.picasso.Picasso
import javax.inject.Inject

class DetailDecorator
@Inject
constructor(
        val activity: BaseActivity,
        val imageRepo: ImageRepository,
        private val layoutManager: LinearLayoutManager,
        private val adapter: CommentAdapter,
        alertDialog: AlertDialog
) : DetailUserInterface, BaseDecorator(alertDialog) {

    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar
    @BindView(R.id.avatar)
    lateinit var avatar: ImageView
    @BindView(R.id.title)
    lateinit var title: TextView
    @BindView(R.id.body)
    lateinit var body: TextView
    @BindView(R.id.author)
    lateinit var author: TextView
    @BindView(R.id.comments_list)
    lateinit var list: RecyclerView
    @BindView(R.id.swipeRefreshLayout)
    lateinit var swipeRefresh: SwipeRefreshLayout

    private var delegate: DetailUserInterface.Delegate? = null

    private var refreshListener: SwipeRefreshLayout.OnRefreshListener =
            SwipeRefreshLayout.OnRefreshListener { delegate?.onRefresh() }

    private val iconTarget = object : ImageTarget() {
        override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom) {
            avatar.setImageBitmap(bitmap)
        }
    }

    fun bind(view: View) {
        ButterKnife.bind(this, view)
        initToolbar()
        list.layoutManager = layoutManager
        list.itemAnimator = DefaultItemAnimator()
        list.adapter = adapter
        swipeRefresh.setOnRefreshListener(refreshListener)
    }

    override fun initialize(delegate: DetailUserInterface.Delegate, post: PostUiModel) {
        this.delegate = delegate
        toolbar.title = post.title
        title.text = post.title
        body.text = post.body
        author.text = post.name
        imageRepo.getCurrentIcon("https://api.adorable.io/avatars/256/${post.email}.png", iconTarget)
        setWaitingMode(true)
    }

    override fun showComments(comments: List<Comment>) {
        setWaitingMode(false)
        list.visibility = if (comments.isEmpty()) View.INVISIBLE else View.VISIBLE
        adapter.swap(comments)
    }

    override fun showError(error: NetworkResultCode) {
        handleResult(error)
    }

    private fun setWaitingMode(waitingMode: Boolean) {
        swipeRefresh.isRefreshing = waitingMode
    }

    private fun initToolbar() {
        activity.setSupportActionBar(toolbar)
        val actionBar = activity.supportActionBar
        actionBar?.setDisplayShowTitleEnabled(false)
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }
}

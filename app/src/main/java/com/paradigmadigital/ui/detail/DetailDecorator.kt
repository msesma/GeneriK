package com.paradigmadigital.ui.detail

import android.graphics.Bitmap
import android.support.annotation.StringRes
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import com.paradigmadigital.R
import com.paradigmadigital.api.images.ImageRepository
import com.paradigmadigital.api.images.ImageTarget
import com.paradigmadigital.domain.entities.PostUiModel
import com.paradigmadigital.ui.BaseActivity
import com.squareup.picasso.Picasso
import javax.inject.Inject

class DetailDecorator
@Inject
constructor(
        val activity: BaseActivity,
        val imageRepo: ImageRepository
) : DetailUserInterface {

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
    @BindView(R.id.comments)
    lateinit var comments: TextView


    private val iconTarget = object : ImageTarget() {
        override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom) {
            avatar.setImageBitmap(bitmap)
        }
    }

    fun bind(view: View) {
        ButterKnife.bind(this, view)
        initToolbar()
    }

    override fun initialize(post: PostUiModel) {
        toolbar.title = post.title
        title.text = post.title
        body.text = post.body
        author.text = post.name
        imageRepo.getCurrentIcon("https://api.adorable.io/avatars/256/${post.email}.png", iconTarget)
    }

    override fun setComments(numComments: Int) {
        comments.text = activity.resources.getQuantityString(R.plurals.num_comments, numComments, numComments)
    }

    override fun showError(error: Exception) {
        showToast(R.string.server_error)
    }

    private fun showToast(@StringRes text: Int) = Toast.makeText(activity, text, Toast.LENGTH_SHORT).show()

    private fun initToolbar() {
        activity.setSupportActionBar(toolbar)
        val actionBar = activity.supportActionBar
        actionBar?.setDisplayShowTitleEnabled(false)
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }
}

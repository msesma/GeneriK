package com.paradigmadigital.ui.main

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.paradigmadigital.R
import com.paradigmadigital.api.images.ImageRepository
import com.paradigmadigital.api.images.ImageTarget
import com.paradigmadigital.domain.entities.Post
import com.squareup.picasso.Picasso

class PostViewHolder(
        itemView: ViewGroup,
        val imageRepo: ImageRepository
) : RecyclerView.ViewHolder(itemView) {

    @BindView(R.id.title)
    lateinit var title: TextView
    @BindView(R.id.avatar)
    lateinit var avatar: ImageView

    private val iconTarget = object : ImageTarget() {
        override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom) {
            avatar.setImageBitmap(bitmap)
        }
    }

    lateinit private var post: Post
    private var postClickListener: PostClickListener? = null

    private val context: Context
    private val resources: Resources

    init {
        ButterKnife.bind(this, itemView)
        avatar.setImageDrawable(ColorDrawable(Color.TRANSPARENT))
        resources = itemView.resources
        context = itemView.context
    }

    fun bind(post: Post, postClickListener: PostClickListener?) {
        this.post = post
        this.postClickListener = postClickListener
        configureView()
    }

    private fun configureView() {
        title.text = post.title
        imageRepo.getCurrentIcon("https://api.adorable.io/avatars/256/${post.email}.png", iconTarget)
    }

    @OnClick(R.id.main_row)
    internal fun onRowClick() {
        postClickListener?.onClick(adapterPosition)
    }
}

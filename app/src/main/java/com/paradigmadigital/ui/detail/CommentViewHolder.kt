package com.paradigmadigital.ui.detail

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
import com.paradigmadigital.R
import com.paradigmadigital.api.images.ImageRepository
import com.paradigmadigital.api.images.ImageTarget
import com.paradigmadigital.api.model.Comment
import com.squareup.picasso.Picasso

class CommentViewHolder(
        itemView: ViewGroup,
        val imageRepo: ImageRepository
) : RecyclerView.ViewHolder(itemView) {

    @BindView(R.id.comenter)
    lateinit var comenter: TextView
    @BindView(R.id.body)
    lateinit var commentBody: TextView
    @BindView(R.id.comenter_avatar)
    lateinit var avatar: ImageView

    private val iconTarget = object : ImageTarget() {
        override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom) {
            avatar.setImageBitmap(bitmap)
        }
    }

    lateinit private var comment: Comment

    private val context: Context
    private val resources: Resources

    init {
        ButterKnife.bind(this, itemView)
        resources = itemView.resources
        context = itemView.context
    }

    fun bind(comment: Comment) {
        this.comment = comment
        configureView()
    }

    private fun configureView() {
        avatar.setImageDrawable(ColorDrawable(Color.TRANSPARENT))
        with(comment) {
            comenter.text = "$name, $email"
            commentBody.text = body
            imageRepo.getCurrentIcon("https://api.adorable.io/avatars/256/$email.png", iconTarget)
        }

    }
}

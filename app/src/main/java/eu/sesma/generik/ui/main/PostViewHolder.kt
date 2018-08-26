package eu.sesma.generik.ui.main

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
import eu.sesma.generik.R
import eu.sesma.generik.api.images.ImageRepository
import eu.sesma.generik.api.images.ImageTarget
import eu.sesma.generik.domain.entities.PostUiModel
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

    lateinit private var post: PostUiModel
    private var postClickListener: PostClickListener? = null

    private val context: Context
    private val resources: Resources

    init {
        ButterKnife.bind(this, itemView)
        resources = itemView.resources
        context = itemView.context
    }

    fun bind(post: PostUiModel, postClickListener: PostClickListener?) {
        this.post = post
        this.postClickListener = postClickListener
        configureView()
    }

    private fun configureView() {
        avatar.setImageDrawable(ColorDrawable(Color.TRANSPARENT))
        title.text = post.title
        imageRepo.getCurrentIcon("https://api.adorable.io/avatars/256/${post.email}.png", iconTarget)
    }

    @OnClick(R.id.main_row)
    internal fun onRowClick() {
        postClickListener?.onClick(adapterPosition)
    }
}

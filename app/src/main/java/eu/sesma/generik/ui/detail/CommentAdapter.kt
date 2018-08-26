package eu.sesma.generik.ui.detail

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import eu.sesma.generik.R
import eu.sesma.generik.api.images.ImageRepository
import eu.sesma.generik.api.model.Comment
import javax.inject.Inject

class CommentAdapter
@Inject constructor(
        val imageRepo: ImageRepository
) : RecyclerView.Adapter<CommentViewHolder>() {

    private var comments: List<Comment> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.comment_line, parent, false) as ViewGroup
        return CommentViewHolder(view, imageRepo)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) =
            holder.bind(comments[position])

    override fun getItemCount() = comments.size

    fun swap(comments: List<Comment>) {
        this.comments = comments
        notifyDataSetChanged()
    }
}

package com.paradigmadigital.ui.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.paradigmadigital.R
import com.paradigmadigital.api.images.ImageRepository
import com.paradigmadigital.domain.entities.PostUiModel
import javax.inject.Inject

class PostAdapter
@Inject constructor(
        val imageRepo: ImageRepository
) : RecyclerView.Adapter<PostViewHolder>() {

    private var posts: List<PostUiModel> = listOf()
    private var postClickListener: PostClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.main_line, parent, false) as ViewGroup
        return PostViewHolder(view, imageRepo)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) =
            holder.bind(posts[position], postClickListener)

    override fun getItemCount() = posts.size

    fun swap(posts: List<PostUiModel>) {
        this.posts = posts
        notifyDataSetChanged()
    }

    fun setClickListener(postClickListener: PostClickListener) {
        this.postClickListener = postClickListener
    }

    fun getItemAtPosition(position: Int): PostUiModel {
        return posts[position]
    }
}

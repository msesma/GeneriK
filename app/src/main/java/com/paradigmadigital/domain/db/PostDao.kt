package com.paradigmadigital.domain.db

import android.arch.persistence.room.*
import com.paradigmadigital.domain.entities.Post
import io.reactivex.Flowable
import io.reactivex.Single


@Dao
abstract class PostDao {

    fun insert(posts: List<Post>) {
        posts.forEach(this::insert)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(item: Post)

    @Update
    abstract fun updateItem(item: Post)

    @Query("SELECT * FROM posts")
    abstract fun getPosts(): Flowable<List<Post>>

    @Query("SELECT * FROM posts WHERE id = :id")
    abstract fun getPost(id: Int): Single<Post>
}
package com.paradigmadigital.domain.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.paradigmadigital.domain.entities.Post
import com.paradigmadigital.domain.entities.PostUiModel
import io.reactivex.Flowable


@Dao
abstract class PostDao {

    fun insert(posts: List<Post>) {
        posts.forEach(this::insert)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(item: Post)

    @Query("SELECT * FROM posts " +
            "LEFT JOIN (SELECT aid, email, name FROM authors) " +
            "ON userId = aid")
    abstract fun getPosts(): Flowable<List<PostUiModel>>
}
package com.paradigmadigital.domain.mappers

import com.paradigmadigital.api.model.PostData
import com.paradigmadigital.api.model.UserData
import com.paradigmadigital.domain.entities.Post
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class PostsUserMapper
@Inject constructor() : BiFunction<List<PostData>, List<UserData>, List<Post>> {

    override fun apply(posts: List<PostData>, users: List<UserData>) = posts.map { postData ->
        val user = users.filter { userData -> userData.id == postData.userId }
        Post(
                id = postData.id ?: 0,
                title = postData.title ?: "",
                body = postData.body ?: "",
                name = if (user.isNotEmpty()) user.get(0).name ?: "" else "",
                email = if (user.isNotEmpty()) user.get(0).email ?: "" else ""
        )
    }

}
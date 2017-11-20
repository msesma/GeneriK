package com.paradigmadigital.domain.mappers

import com.paradigmadigital.api.model.PostData
import com.paradigmadigital.domain.entities.Post
import javax.inject.Inject

class PostMapper
@Inject constructor() : Mapper<Post, PostData> {

    override fun map(input: PostData) =
            Post(
                    id = input.id ?: 0,
                    userId = input.userId ?: 0,
                    title = input.title ?: "",
                    body = input.body ?: ""
            )
}
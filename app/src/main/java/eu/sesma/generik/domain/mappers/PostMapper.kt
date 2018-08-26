package eu.sesma.generik.domain.mappers

import eu.sesma.generik.api.model.PostData
import eu.sesma.generik.domain.entities.Post
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
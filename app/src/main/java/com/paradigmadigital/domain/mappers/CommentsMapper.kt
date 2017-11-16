package com.paradigmadigital.domain.mappers

import com.paradigmadigital.api.model.CommentData
import javax.inject.Inject

class CommentsMapper
@Inject constructor() : Mapper<Int, List<CommentData>> {

    override fun map(input: List<CommentData>) = input.size

}
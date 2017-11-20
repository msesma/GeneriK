package com.paradigmadigital.domain.mappers

import com.paradigmadigital.api.model.UserData
import com.paradigmadigital.domain.entities.Author
import javax.inject.Inject

class AuthorMapper
@Inject constructor() : Mapper<Author, UserData> {

    override fun map(input: UserData) =
            Author(
                    aid = input.id ?: 0,
                    name = input.name ?: "",
                    email = input.email ?: ""
            )
}
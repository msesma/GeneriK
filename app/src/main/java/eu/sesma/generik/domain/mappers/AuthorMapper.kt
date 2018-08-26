package eu.sesma.generik.domain.mappers

import eu.sesma.generik.api.model.UserData
import eu.sesma.generik.domain.entities.Author
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
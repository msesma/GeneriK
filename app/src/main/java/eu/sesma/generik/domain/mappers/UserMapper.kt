package eu.sesma.generik.domain.mappers

import eu.sesma.generik.api.model.Login
import eu.sesma.generik.domain.entities.User
import javax.inject.Inject


class UserMapper @Inject constructor() : Mapper<User, Login> {
    override fun map(input: Login) = User(
            uid = input.uid,
            name = input.name,
            phone = input.phone,
            email = input.email
     )
}
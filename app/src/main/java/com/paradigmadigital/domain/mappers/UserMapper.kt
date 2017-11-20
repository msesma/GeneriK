package com.paradigmadigital.domain.mappers

import com.paradigmadigital.api.model.Login
import com.paradigmadigital.domain.entities.User
import javax.inject.Inject


class UserMapper @Inject constructor() : Mapper<User, Login> {
    override fun map(input: Login) = User(
            uid = input.uid,
            name = input.name,
            phone = input.phone,
            email = input.email
     )
}
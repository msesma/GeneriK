package com.paradigmadigital.api.mappers

import com.paradigmadigital.api.model.Login
import com.paradigmadigital.domain.entities.User
import com.paradigmadigital.domain.mappers.Mapper
import javax.inject.Inject


class UserMapper @Inject constructor() : Mapper<Login, User> {
    override fun map(input: User): Login {
        val login = Login()
        login.uid = input.uid
        login.name = input.name
        login.registerDate = input.registerDate
        login.phone = input.phone
        login.email = input.email
        return login
    }
}
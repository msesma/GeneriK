package com.paradigmadigital.api.mappers

import com.paradigmadigital.domain.entities.User
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import java.util.*


class UserMapperShould {
    private lateinit var mapper: UserMapper

    @Before
    fun setUp() {
        mapper = UserMapper()
    }

    @Test
    fun mapInputInOutputOnMap() {
        val user = User(uid = "123", name = "Bob", registerDate = Date(0), phone = "12345678", email = "bon@acme.com")

        val login = mapper.map(user)

        assertThat(login.uid).isEqualTo(user.uid)
        assertThat(login.name).isEqualTo(user.name)
        assertThat(login.registerDate).isEqualTo(user.registerDate.time)
        assertThat(login.phone).isEqualTo(user.phone)
        assertThat(login.email).isEqualTo(user.email)
    }
}
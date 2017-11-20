package com.paradigmadigital.domain.mappers

import com.paradigmadigital.api.model.Login
import org.assertj.core.api.Assertions
import org.junit.Before
import org.junit.Test


class UserMapperShould {
    private lateinit var mapper: UserMapper

    @Before
    fun setUp() {
        mapper = UserMapper()
    }

    @Test
    fun mapInputInOutputOnMap() {
        val login = Login(
                uid = "123",
                name = "Bob",
                phone = "12345678",
                email = "bon@acme.com",
                token = "hlfhslgrtlg5uypa8eghñe8gñae8ygñehgañfldg"
        )

        val user = mapper.map(login)

        Assertions.assertThat(user.uid).isEqualTo(login.uid)
        Assertions.assertThat(user.name).isEqualTo(login.name)
        Assertions.assertThat(user.phone).isEqualTo(login.phone)
        Assertions.assertThat(user.email).isEqualTo(login.email)
    }
}
package com.paradigmadigital.domain.mappers

import com.paradigmadigital.api.model.Login
import org.assertj.core.api.Assertions
import org.junit.Before
import org.junit.Test
import java.util.*


class LoginMapperShould {
    private lateinit var mapper: LoginMapper

    @Before
    fun setUp() {
        mapper = LoginMapper()
    }

    @Test
    fun mapInputInOutputOnMap() {
        val login = Login()
        login.uid = "123"
        login.name = "Bob"
        login.registerDate = Date(0).time
        login.phone = "12345678"
        login.email = "bon@acme.com"
        login.token = "hlfhslgrtlg5uypa8eghñe8gñae8ygñehgañfldg"

        val user = mapper.map(login)

        Assertions.assertThat(user.uid).isEqualTo(login.uid)
        Assertions.assertThat(user.name).isEqualTo(login.name)
        Assertions.assertThat(user.registerDate.time).isEqualTo(login.registerDate)
        Assertions.assertThat(user.phone).isEqualTo(login.phone)
        Assertions.assertThat(user.email).isEqualTo(login.email)
        Assertions.assertThat(user.token).isEqualTo(login.token)
    }
}
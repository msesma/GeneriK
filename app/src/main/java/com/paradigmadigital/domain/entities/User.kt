package com.paradigmadigital.domain.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*


@Entity(tableName = "user")
data class User(
        @PrimaryKey
        var oneRow: String = "0",
        var uid: String = "",
        var name: String = "",
        var registerDate: Date = Date(0),
        var phone: String = "",
        var email: String = "",
        var code: String = "",
        var codeDate: Date = Date(0),
        var loggedIn: Int = 0
)
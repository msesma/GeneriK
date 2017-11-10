package com.paradigmadigital.domain.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey


@Entity(tableName = "user")
data class User(
        @PrimaryKey
        var uid: String = "",
        var name: String = "",
        var phone: String = "",
        var email: String = ""
)
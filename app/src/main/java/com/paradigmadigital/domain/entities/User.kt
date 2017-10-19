package com.paradigmadigital.domain.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*


@Entity(tableName = "user")
data class User(
        @PrimaryKey
        var uid: String,
        var name: String,
        var registerDate: Date,
        var phone: String,
        var email: String
)
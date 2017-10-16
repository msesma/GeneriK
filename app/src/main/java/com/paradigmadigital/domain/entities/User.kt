package com.paradigmadigital.domain.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*


@Entity(tableName = "user")
data class User(
        var name: String,
        var registerDate: Date,
        var phone: String,
        @PrimaryKey var email: String
)
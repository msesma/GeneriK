package com.paradigmadigital.domain.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey


@Entity(tableName = "authors")
data class Author(
        @PrimaryKey
        var aid: Int = 0,
        var email: String = "",
        var name: String = ""
)
package com.paradigmadigital.domain.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "posts")
data class Post(
        @PrimaryKey
        var id: Int = 0,
        var title: String = "",
        var body: String = "",
        var name: String = "",
        var email: String = ""
) : Serializable

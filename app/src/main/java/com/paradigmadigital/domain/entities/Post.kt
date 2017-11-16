package com.paradigmadigital.domain.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "posts")
data class Post(
        @PrimaryKey
        val id: Int = 0,
        val title: String = "",
        val body: String = "",
        val name: String = "",
        val email: String = ""
) : Serializable

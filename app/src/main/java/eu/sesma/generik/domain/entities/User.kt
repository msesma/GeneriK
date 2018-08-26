package eu.sesma.generik.domain.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey


@Entity(tableName = "user")
data class User(
        @PrimaryKey
        var email: String = "",
        var name: String = "",
        var phone: String = "",
        var uid: String = ""
)
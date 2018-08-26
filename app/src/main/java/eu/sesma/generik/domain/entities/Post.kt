package eu.sesma.generik.domain.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "posts")
data class Post(
        @PrimaryKey
        var id: Int = 0,
        var userId: Int = 0,
        var title: String = "",
        var body: String = ""
)

data class PostUiModel(
        @ColumnInfo
        var id: Int = 0,
        @ColumnInfo
        var title: String? = "",
        @ColumnInfo
        var body: String? = "",
        @ColumnInfo
        var name: String? = "",
        @ColumnInfo
        var email: String? = ""
) : Serializable
package eu.sesma.generik.domain.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import eu.sesma.generik.domain.converter.DateConverter
import eu.sesma.generik.domain.entities.Author
import eu.sesma.generik.domain.entities.Post
import eu.sesma.generik.domain.entities.User


@Database(entities = arrayOf(User::class, Post::class, Author::class),
        version = 1)
@TypeConverters(DateConverter::class)
abstract class Database : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun postDao(): PostDao
    abstract fun authorDao(): AuthorDao
}
package com.paradigmadigital.domain.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.paradigmadigital.domain.converter.DateConverter
import com.paradigmadigital.domain.entities.Post
import com.paradigmadigital.domain.entities.User


@Database(entities = arrayOf(User::class, Post::class),
        version = 1)
@TypeConverters(DateConverter::class)
abstract class Database : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun postDao(): PostDao
}
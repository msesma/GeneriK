package com.paradigmadigital.domain.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.paradigmadigital.domain.entities.User
import java.util.*


@Dao
abstract class UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(item: User)

    @Update
    abstract fun updateItem(item: User)

    @Query("SELECT * FROM user")
    abstract fun get(): LiveData<User>

    @Query("SELECT * FROM user")
    abstract fun getUser(): User

    @Query("UPDATE user SET code = :code, codeDate = :date WHERE email = :email")
    abstract fun setCode(code: String, date: Date, email: String): Int
}
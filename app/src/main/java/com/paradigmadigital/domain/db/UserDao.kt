package com.paradigmadigital.domain.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.paradigmadigital.domain.entities.User


@Dao
abstract class UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(item: User)

    @Update
    abstract fun updateItem(item: User)

    @Query("SELECT * FROM user")
    abstract fun get(): LiveData<User>
}
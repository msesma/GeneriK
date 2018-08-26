package eu.sesma.generik.domain.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import eu.sesma.generik.domain.entities.User


@Dao
abstract class UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(item: User)

    @Update
    abstract fun updateItem(item: User)

    @Query("SELECT * FROM user")
    abstract fun get(): LiveData<User>

    @Query("SELECT * FROM user")
    abstract fun getUser(): User?
}
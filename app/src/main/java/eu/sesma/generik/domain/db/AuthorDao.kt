package eu.sesma.generik.domain.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import eu.sesma.generik.domain.entities.Author
import io.reactivex.Single


@Dao
abstract class AuthorDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(item: Author)

    @Query("SELECT * FROM authors WHERE aid = :aid")
    abstract fun getAuthor(aid: Int): Single<Author>
}
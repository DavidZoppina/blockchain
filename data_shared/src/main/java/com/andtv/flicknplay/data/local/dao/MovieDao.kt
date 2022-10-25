

package com.andtv.flicknplay.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.andtv.flicknplay.model.data.local.MovieDbModel
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single

/**
 *
 */
@Dao
interface MovieDao {

    @Query("SELECT * FROM movie")
    fun getAll(): Single<List<MovieDbModel>>

    @Query("SELECT * FROM movie WHERE id = :id")
    fun getById(id: Int): Maybe<MovieDbModel>

    @Insert
    fun create(model: MovieDbModel): Completable

    @Update
    fun update(model: MovieDbModel): Completable

    @Delete
    fun delete(model: MovieDbModel): Completable
}

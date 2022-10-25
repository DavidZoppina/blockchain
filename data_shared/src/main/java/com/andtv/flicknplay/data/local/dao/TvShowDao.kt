

package com.andtv.flicknplay.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.andtv.flicknplay.model.data.local.TvShowDbModel
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single

/**
 *
 */
@Dao
interface TvShowDao {

    @Query("SELECT * FROM tv_show")
    fun getAll(): Single<List<TvShowDbModel>>

    @Query("SELECT * FROM tv_show WHERE id = :id")
    fun getById(id: Int): Maybe<TvShowDbModel>

    @Insert
    fun create(model: TvShowDbModel): Completable

    @Update
    fun update(model: TvShowDbModel): Completable

    @Delete
    fun delete(model: TvShowDbModel): Completable
}

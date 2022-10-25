

package com.andtv.flicknplay.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.andtv.flicknplay.data.local.dao.MovieDao
import com.andtv.flicknplay.data.local.dao.TvShowDao
import com.andtv.flicknplay.model.data.local.MovieDbModel
import com.andtv.flicknplay.model.data.local.TvShowDbModel

/**
 *
 */
@Database(
        entities = [
            MovieDbModel::class,
            TvShowDbModel::class
        ],
        version = 1,
        exportSchema = false
)
abstract class MediaDb : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    abstract fun tvShowDao(): TvShowDao
}

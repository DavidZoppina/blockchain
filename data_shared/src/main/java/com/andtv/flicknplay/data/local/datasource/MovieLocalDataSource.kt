

package com.andtv.flicknplay.data.local.datasource

import com.andtv.flicknplay.data.local.dao.MovieDao
import com.andtv.flicknplay.model.data.local.MovieDbModel
import javax.inject.Inject

/**
 *
 */
class MovieLocalDataSource @Inject constructor(
    private val movieDao: MovieDao
) {

    fun saveFavoriteMovie(movieDbModel: MovieDbModel) =
            movieDao.create(movieDbModel)

    fun deleteFavoriteMovie(movieDbModel: MovieDbModel) =
            movieDao.delete(movieDbModel)

    fun getMovies() =
            movieDao.getAll()

    fun getById(movieDbModel: MovieDbModel) =
            movieDao.getById(movieDbModel.id)
}



package com.andtv.flicknplay.recommendation.data.repository

import com.andtv.flicknplay.model.data.mapper.toDomainModel
import com.andtv.flicknplay.presentation.platform.Resource
import com.andtv.flicknplay.recommendation.R
import com.andtv.flicknplay.recommendation.data.remote.datasource.MovieRemoteDataSource
import javax.inject.Inject

/**
 *
 */
class MovieRepository @Inject constructor(
    private val resource: Resource,
    private val movieRemoteDataSource: MovieRemoteDataSource
) {

    fun getPopularMovies(page: Int) =
            movieRemoteDataSource.getPopularMovies(page)
                    .map {
                        val source = resource.getStringResource(R.string.source_tmdb)
                        it.toDomainModel(source)
                    }
}

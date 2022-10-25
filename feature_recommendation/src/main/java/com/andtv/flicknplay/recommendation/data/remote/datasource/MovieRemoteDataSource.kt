
package com.andtv.flicknplay.recommendation.data.remote.datasource

import com.andtv.flicknplay.recommendation.data.remote.api.MovieTmdbApi
import javax.inject.Inject
import javax.inject.Named

/**
 *
 */
class MovieRemoteDataSource @Inject constructor(
    @Named("tmdbApiKey") private val tmdbApiKey: String,
    @Named("tmdbFilterLanguage") private val tmdbFilterLanguage: String,
    private val movieTmdbApi: MovieTmdbApi
) {

    fun getPopularMovies(page: Int) =
            movieTmdbApi.getPopularMovies(tmdbApiKey, tmdbFilterLanguage, page)
}

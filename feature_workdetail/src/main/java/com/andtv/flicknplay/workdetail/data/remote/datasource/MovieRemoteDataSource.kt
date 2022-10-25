

package com.andtv.flicknplay.workdetail.data.remote.datasource

import com.andtv.flicknplay.model.data.remote.FlicknplayTitleResponseWrapper
import com.andtv.flicknplay.workdetail.data.remote.api.MovieDetailFlicknplayApi
import com.andtv.flicknplay.workdetail.data.remote.api.MovieDetailTmdbApi
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Named

/**
 *
 */
class MovieRemoteDataSource @Inject constructor(
    @Named("tmdbApiKey") private val tmdbApiKey: String,
    @Named("tmdbFilterLanguage") private val tmdbFilterLanguage: String,
    private val movieDetailTmdbApi: MovieDetailTmdbApi,
    private val movieDetailFlicknplayApi: MovieDetailFlicknplayApi
) {

    fun getMovieDetails(movieId: Int, page: Int): Single<FlicknplayTitleResponseWrapper> = movieDetailFlicknplayApi.getTitle(movieId)


    fun getCastByMovie(movieId: Int) =
      movieDetailTmdbApi.getCastByMovie(movieId, tmdbApiKey, tmdbFilterLanguage)


    fun getRecommendationByMovie(movieId: Int, page: Int) =
            movieDetailTmdbApi.getRecommendationByMovie(movieId, tmdbApiKey, tmdbFilterLanguage, page)

    fun getSimilarByMovie(movieId: Int, page: Int) =
        movieDetailFlicknplayApi.getRelatedByMovie(movieId)

    //Review source not needed
    fun getReviewByMovie(movieId: Int, page: Int) =
            movieDetailTmdbApi.getReviewByMovie(movieId, tmdbApiKey, tmdbFilterLanguage, page)

    fun getVideosByMovie(movieId: Int) =
        movieDetailFlicknplayApi.getVideosByMovie(movieId)

   // fun getVideosByMovie(movieId: Int) =
   //     movieDetailTmdbApi.getVideosByMovie(movieId, tmdbApiKey, tmdbFilterLanguage)

    fun getEpisodesBySeasonNumber(movieId: Int, seasonNumber: Int) =
        movieDetailFlicknplayApi.getEpisodeBySeasonId(movieId, seasonNumber)

    fun updateVideoHistory(videoId:Int, seekTime: Int, duration:Int) =
        movieDetailFlicknplayApi.updateVideoHistory(
            videoId, seekTime, duration
        )
}

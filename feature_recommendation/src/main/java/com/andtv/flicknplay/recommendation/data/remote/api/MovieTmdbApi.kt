

package com.andtv.flicknplay.recommendation.data.remote.api

import com.andtv.flicknplay.model.data.remote.MovieResponse
import com.andtv.flicknplay.model.data.remote.PageResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 *
 */
interface MovieTmdbApi {

    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Single<PageResponse<MovieResponse>>
}



package com.andtv.flicknplay.search.data.remote.api

import com.andtv.flicknplay.model.data.remote.MovieResponse
import com.andtv.flicknplay.model.data.remote.PageResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 *
 */
interface SearchMovieTmdbApi {

    @GET("search/movie")
    fun searchMoviesByQuery(
        @Query("api_key") apiKey: String,
        @Query("query") query: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Single<PageResponse<MovieResponse>>
}

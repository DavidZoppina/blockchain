

package com.andtv.flicknplay.workdetail.data.remote.api

import com.andtv.flicknplay.model.data.remote.CastListResponse
import com.andtv.flicknplay.model.data.remote.MovieResponse
import com.andtv.flicknplay.model.data.remote.PageResponse
import com.andtv.flicknplay.workdetail.data.remote.model.ReviewResponse
import com.andtv.flicknplay.workdetail.data.remote.model.VideoListResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 *
 */
interface MovieDetailTmdbApi {

    @GET("movie/{movie_id}/credits")
    fun getCastByMovie(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Single<CastListResponse>

    @GET("movie/{movie_id}/recommendations")
    fun getRecommendationByMovie(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Single<PageResponse<MovieResponse>>

    @GET("movie/{movie_id}/similar")
    fun getSimilarByMovie(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Single<PageResponse<MovieResponse>>

    @GET("movie/{movie_id}/reviews")
    fun getReviewByMovie(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Single<PageResponse<ReviewResponse>>

    @GET("movie/{movie_id}/videos")
    fun getVideosByMovie(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Single<VideoListResponse>
}

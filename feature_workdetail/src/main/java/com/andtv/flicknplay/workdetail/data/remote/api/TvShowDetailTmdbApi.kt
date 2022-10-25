

package com.andtv.flicknplay.workdetail.data.remote.api

import com.andtv.flicknplay.model.data.remote.CastListResponse
import com.andtv.flicknplay.model.data.remote.PageResponse
import com.andtv.flicknplay.model.data.remote.TvShowResponse
import com.andtv.flicknplay.workdetail.data.remote.model.ReviewResponse
import com.andtv.flicknplay.workdetail.data.remote.model.VideoListResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 *
 */
interface TvShowDetailTmdbApi {

    @GET("tv/{tv_id}/credits")
    fun getCastByTvShow(
        @Path("tv_id") tvId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Single<CastListResponse>

    @GET("tv/{tv_id}/recommendations")
    fun getRecommendationByTvShow(
        @Path("tv_id") tvId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Single<PageResponse<TvShowResponse>>

    @GET("tv/{tv_id}/similar")
    fun getSimilarByTvShow(
        @Path("tv_id") tvId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Single<PageResponse<TvShowResponse>>

    @GET("tv/{tv_id}/reviews")
    fun getReviewByTvShow(
        @Path("tv_id") tvId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Single<PageResponse<ReviewResponse>>

    @GET("tv/{tv_id}/videos")
    fun getVideosByTvShow(
        @Path("tv_id") tvId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Single<VideoListResponse>
}

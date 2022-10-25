

package com.andtv.flicknplay.workdetail.data.remote.api

import com.andtv.flicknplay.model.data.remote.*
import com.andtv.flicknplay.workdetail.data.remote.model.UpdateHistoryResponse
import com.andtv.flicknplay.workdetail.data.remote.model.VideoListResponse
import io.reactivex.Single
import retrofit2.http.*

/**
 *
 */
interface MovieDetailFlicknplayApi {

    @GET("titles/{id}")
    fun getTitle(
        @Path("id") id: Int
    ): Single<FlicknplayTitleResponseWrapper>

    @GET("titles/{id}/related")
    fun getRelatedByMovie(
        @Path("id") id: Int
    ): Single<RelatedTitlesResponse<FlicknplayMovieResponse>>

    @GET("movie/{movie_id}/videos")
    fun getVideosByMovie(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Single<VideoListResponse>

    fun getCastByMovie(movieId: Int): Any
    fun getVideosByMovie(movie_id: Int): Single<VideoListResponse>


    @GET("titles/{id}/")
    fun getEpisodeBySeasonId(
        @Path("id") id: Int,
        @Query("seasonNumber") seasonNumber: Int
    ): Single<FlicknplayTitleResponseWrapper>

    @POST("video-history/{video}/{seekTime}/{duration}/log-update")
    fun updateVideoHistory(
        @Path("video") video:Int,
        @Path("seekTime") seekTime:Int,
        @Path("duration") duration:Int,
    ): Single<UpdateHistoryResponse>

}

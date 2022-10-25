

package com.andtv.flicknplay.castdetail.data.remote.api

import com.andtv.flicknplay.model.data.remote.CastResponse
import com.andtv.flicknplay.model.data.remote.CastWorkListResponse
import com.andtv.flicknplay.model.data.remote.MovieResponse
import com.andtv.flicknplay.model.data.remote.TvShowResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 *
 */
interface CastTmdbApi {

    @GET("person/{person_id}")
    fun getCastDetails(
        @Path("person_id") personId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Single<CastResponse>

    @GET("person/{person_id}/movie_credits")
    fun getMovieCredits(
        @Path("person_id") personId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Single<CastWorkListResponse<MovieResponse>>

    @GET("person/{person_id}/tv_credits")
    fun getTvShowCredits(
        @Path("person_id") personId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Single<CastWorkListResponse<TvShowResponse>>
}

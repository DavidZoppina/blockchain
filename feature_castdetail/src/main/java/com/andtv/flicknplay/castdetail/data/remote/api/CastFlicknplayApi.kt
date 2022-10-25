

package com.andtv.flicknplay.castdetail.data.remote.api

import com.andtv.flicknplay.model.data.remote.*
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

/**
 *
 */
interface CastFlicknplayApi {

    @GET("people/{id}")
    fun getCastDetails(
        @Path("id") id: Int
    ): Single<CastResponseWrapper>


    fun getMovieCredits(castId: Int): Single<CastResponseWrapper>
    fun getTvShowCredits(castId: Int): Any


}


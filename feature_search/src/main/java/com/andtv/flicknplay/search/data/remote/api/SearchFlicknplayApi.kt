

package com.andtv.flicknplay.search.data.remote.api

import com.andtv.flicknplay.model.data.remote.FlicknplayMovieResponse
import com.andtv.flicknplay.model.data.remote.FlicknplaySearchResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

/**
 *
 */
interface SearchFlicknplayApi {

    @GET("search/{query}")
    fun searchMoviesByQuery(
        @Path("query") query: String,
    ): Single<FlicknplaySearchResponse<FlicknplayMovieResponse>>
}

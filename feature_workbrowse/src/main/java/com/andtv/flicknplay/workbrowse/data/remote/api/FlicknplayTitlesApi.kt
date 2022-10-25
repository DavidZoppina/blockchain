/*
 * Copyright (C) 2021 Flicknplay
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.andtv.flicknplay.workbrowse.data.remote.api

import com.andtv.flicknplay.model.data.remote.*
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 *
 */
interface FlicknplayTitlesApi {

    @GET("titles/{id}")
    fun getTitle(
        @Path("id") id: Int
    ): Call<FlicknplayMovieResponse>

    @GET("titles")
    fun getTitlesByGenre(
        @Query("genre") genres: String? = "",
        @Query("language") language: String,
        @Query("country") country: String,
        @Query("includeAdult") includeAdult: Boolean,
        @Query("onlyStreamable") onlyStreamable: Boolean,
        @Query("page") page: Int,
        @Query("type") type: String = "movie"
    ): Single<FlicknplayPageResponseWrapper<FlicknplayMovieResponse>>

    @GET("lists/{id}")
    fun getList(
        @Path("id") id: Int,
    ): Single<FlicknplayListPageResponseWrapper>


    @GET("video-history")
    fun getContinueWatchingMovies ():Single<VideoHistoryModel>

}

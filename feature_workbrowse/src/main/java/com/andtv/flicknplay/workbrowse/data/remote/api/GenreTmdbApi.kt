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

import com.andtv.flicknplay.workbrowse.data.remote.model.MovieGenreListResponse
import com.andtv.flicknplay.workbrowse.data.remote.model.TvShowGenreListResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 *
 */
interface GenreTmdbApi {

    @GET("genre/movie/list")
    fun getMovieGenres(
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Single<MovieGenreListResponse>

    @GET("genre/tv/list")
    fun getTvShowGenres(
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Single<TvShowGenreListResponse>
}

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

package com.andtv.flicknplay.workbrowse.data.remote.datasource

import com.andtv.flicknplay.workbrowse.data.remote.api.FlicknplayTitlesApi
import com.andtv.flicknplay.workbrowse.data.remote.api.MovieTmdbApi
import java.io.IOException
import javax.inject.Inject
import javax.inject.Named
import timber.log.Timber

/**
 * .
 */
class MovieRemoteDataSource @Inject constructor(
    @Named("tmdbApiKey") private val tmdbApiKey: String,
    @Named("tmdbFilterLanguage") private val tmdbFilterLanguage: String,
    @Named("flicknplayFilterLanguage") private val flicknplayFilterLanguage: String,
    @Named("flicknplayFilterCountry") private val flicknplayFilterCountry: String,
    private val movieTmdbApi: MovieTmdbApi,
    private val movieFlicknplayApi: FlicknplayTitlesApi
) {

    fun getMovie(movieId: Int) =
            try {
                movieFlicknplayApi.getTitle(movieId).execute().body()
            } catch (e: IOException) {
                Timber.e(e, "Error while getting a movie")
                null
            }

    fun getMoviesByGenre(genre: String?, page: Int) =
        movieFlicknplayApi.getTitlesByGenre(genre?.lowercase(), flicknplayFilterLanguage, flicknplayFilterCountry, false, true, page)

    fun getNowPlayingMovies(page: Int) =
        movieFlicknplayApi.getList(574)

    fun getPopularMovies(page: Int) =
        movieFlicknplayApi.getList(114)

    fun getTopRatedMovies(page: Int) =
        movieFlicknplayApi.getList(334)

    fun getUpComingMovies(page: Int) =
        movieFlicknplayApi.getList(5)

    fun getContinueWatchingMovies() =
        movieFlicknplayApi.getContinueWatchingMovies()
}

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

package com.andtv.flicknplay.workbrowse.domain

import com.andtv.flicknplay.workbrowse.domain.model.WorkBrowserDetailsDomainModel
import io.reactivex.Single
import javax.inject.Inject

/**
 * Copyright (C) Flicknplay 20-05-2019.
 */
class GetWorkBrowseDetailsUseCase @Inject constructor(
    private val hasFavoriteUseCase: HasFavoriteUseCase,
    private val getMovieGenresUseCase: GetMovieGenresUseCase,
    private val getTvShowGenresUseCase: GetTvShowGenresUseCase,
    private val continueWatchingMoviesUseCase: ContinueWatchingMoviesUseCase
) {

    operator fun invoke() =
        Single.zip(
            hasFavoriteUseCase(),
            getMovieGenresUseCase(),
            getTvShowGenresUseCase(),
            continueWatchingMoviesUseCase()) {hasFavoriteMovie, movieGenreList, tvShowGenreList, continueWatchingList ->
            WorkBrowserDetailsDomainModel(hasFavoriteMovie, movieGenreList, tvShowGenreList, continueWatchingList)
        }

}

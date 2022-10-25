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

import com.andtv.flicknplay.workbrowse.data.repository.MovieRepository
import javax.inject.Inject

/**
 * Copyright (C) Flicknplay 13-10-2019.
 */
class GetFavoriteMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {

    operator fun invoke() =
            movieRepository.getFavoriteMovies()
}

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

package com.andtv.flicknplay.workdetail.domain

import com.andtv.flicknplay.model.presentation.mapper.toMovieDbModel
import com.andtv.flicknplay.model.presentation.mapper.toTvShowDbModel
import com.andtv.flicknplay.model.presentation.model.WorkType
import com.andtv.flicknplay.model.presentation.model.WorkViewModel
import javax.inject.Inject

/**
 * Copyright (C) 2021 Flicknplay.
 */
class CheckFavoriteWorkUseCase @Inject constructor(
    private val checkFavoriteMovieUseCase: CheckFavoriteMovieUseCase,
    private val checkFavoriteTvShowUseCase: CheckFavoriteTvShowUseCase
) {

    operator fun invoke(workViewModel: WorkViewModel) =
            when (workViewModel.type) {
                WorkType.MOVIE -> checkFavoriteMovieUseCase(workViewModel.toMovieDbModel())
                WorkType.TV_SHOW -> checkFavoriteTvShowUseCase(workViewModel.toTvShowDbModel())
            }
}

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

package com.andtv.flicknplay.model.presentation.mapper

import com.andtv.flicknplay.model.BuildConfig
import com.andtv.flicknplay.model.data.local.MovieDbModel
import com.andtv.flicknplay.model.data.local.TvShowDbModel
import com.andtv.flicknplay.model.domain.HistoryWorkDomainModel
import com.andtv.flicknplay.model.presentation.model.HistoryWorkType
import com.andtv.flicknplay.model.presentation.model.HistoryWorkViewModel
import java.text.SimpleDateFormat
import java.util.*

fun HistoryWorkViewModel.toMovieDbModel() =
    MovieDbModel(id = id)

fun HistoryWorkViewModel.toTvShowDbModel() =
    TvShowDbModel(id = id)

fun HistoryWorkDomainModel.toViewModel() = HistoryWorkViewModel(
    id = id,
    title = title,
    originalLanguage = originalLanguage,
    overview = overview,
    source = source,
    backdropUrl = backdropPath?.let { String.format(BuildConfig.TMDB_LOAD_IMAGE_BASE_URL, it) },
    posterUrl = posterPath?.let { String.format(BuildConfig.TMDB_LOAD_IMAGE_BASE_URL, it) },
    originalTitle = originalTitle,
    releaseDate = releaseDate?.takeUnless { it.isEmpty() || it.isBlank() }
        ?.let {
            SimpleDateFormat("MMM dd, yyyy", Locale.US).format(
                SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(it) as Date
            )
        },
    isFavorite = isFavorite,
    type = HistoryWorkType.TV_SHOW.takeIf { type == HistoryWorkDomainModel.Type.TV_SHOW } ?: HistoryWorkType.MOVIE,
    videos = videos,
    credits = credits,
    isSeries = isSeries
)

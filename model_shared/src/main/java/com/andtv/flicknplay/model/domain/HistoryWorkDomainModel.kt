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

package com.andtv.flicknplay.model.domain

import com.andtv.flicknplay.model.data.remote.MovieCredits

/**
 * C
 */
data class HistoryWorkDomainModel(
    val id: Int= 0,
    val title: String? = null,
    val originalTitle: String? = null,
    val releaseDate: String? = null,
    val originalLanguage: String? = null,
    val overview: String? = null,
    val source: String? = null,
    val backdropPath: String? = null,
    val posterPath: String? = null,
    val popularity: Float = 0f,
    val voteAverage: Float = 0f,
    val voteCount: Float = 0f,
    val isAdult: Boolean = false,
    val type: Type = Type.MOVIE,
    var isFavorite: Boolean = false,
    val videos: VideoDomainModel? = VideoDomainModel(),
    val credits: List<MovieCredits>? = listOf(),
    val isSeries: Boolean? = null,
    val episodeCount:Int = 0,
    val seasons: List<SeasonDomainModel>? = listOf(),
    val seekTime: Int =0
) {

    enum class Type {
        TV_SHOW,
        MOVIE
    }
}

/*
 *
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

package com.andtv.flicknplay.model.data.mapper

import com.andtv.flicknplay.model.data.remote.*
import com.andtv.flicknplay.model.domain.WorkDomainModel

fun WorkResponse.toDomainModel(source: String) = WorkDomainModel(
    id = id,
    title = title,
    originalTitle = originalTitle,
    releaseDate = releaseDate,
    originalLanguage = originalLanguage,
    overview = overview,
    source = source,
    backdropPath = backdropPath,
    posterPath = posterPath,
    popularity = popularity,
    voteAverage = voteAverage,
    voteCount = voteCount,
    isAdult = isAdult,
    type = WorkDomainModel.Type.TV_SHOW.takeIf { this is TvShowResponse }
        ?: WorkDomainModel.Type.MOVIE
)

fun FlicknplayWorkResponse.toDomainModel(source: String) = WorkDomainModel(
    id = id,
    title = title,
    originalTitle = originalTitle,
    releaseDate = releaseDate,
    originalLanguage = originalLanguage,
    overview = overview,
    source = source,
    backdropPath = backdropPath,
    posterPath = posterPath,
    popularity = popularity,
    voteAverage = voteAverage,
    voteCount = voteCount,
    isAdult = isAdult,
    type = WorkDomainModel.Type.MOVIE.takeIf { this is FlicknplayMovieResponse }
        ?: WorkDomainModel.Type.TV_SHOW,
    videos = videos?.toDomainModel(),
    credits = credits,
    isSeries = isSeries,
    episodeCount = episodeCount,
    seasons = seasons?.toDomainModel()

)

fun List<FlicknplayMovieResponse>.toDomainModel(source: String) = map{ it.toDomainModel(source)}

fun FlicknplayTitleResponseWrapper.toDomainModel(source: String) = title.toDomainModel(source)


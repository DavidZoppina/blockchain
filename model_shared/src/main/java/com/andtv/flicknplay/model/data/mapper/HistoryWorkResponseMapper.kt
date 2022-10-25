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
import com.andtv.flicknplay.model.domain.HistoryWorkDomainModel
import com.andtv.flicknplay.model.domain.VideoDomainModel
import com.andtv.flicknplay.model.domain.WorkDomainModel

fun FlicknplayHistoryWorkResponse.toDomainModel(source: String) = HistoryWorkDomainModel(
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
    type = HistoryWorkDomainModel.Type.MOVIE.takeIf { this is FlicknplayHistoryMovieResponse }
        ?: HistoryWorkDomainModel.Type.TV_SHOW,
    videos = videos?.toDomainModel(),
    credits = credits,
    isSeries = isSeries,
    episodeCount = episodeCount,
    seasons = seasons?.toDomainModel(),
    seekTime = seekTime?.let {it}?:0

)


fun HistoryWorkDomainModel.toWorkDomainModel(source: String) = WorkDomainModel(
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
    type = WorkDomainModel.Type.MOVIE.takeIf {  this is HistoryWorkDomainModel }
        ?: WorkDomainModel.Type.TV_SHOW,
    videos = videos?.let{ video->
        var list = arrayListOf<VideoDomainModel>()
        list.add(video)
        list
    } ?: listOf(),
    credits = credits,
    isSeries = isSeries,
    episodeCount = episodeCount,
    seasons = seasons,
    seekTime = seekTime


)

fun List<FlicknplayHistoryMovieResponse>.toDomainModel(source: String) = map{ it.toDomainModel(source)}

fun VideoHistoryModel.toDomainModel(source: String) = history.toDomainModel(source)


